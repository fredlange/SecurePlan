package se.secureplan.app.feature.export

import android.content.Context
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import se.secureplan.app.core.domain.model.*
import se.secureplan.app.core.domain.repository.*
import se.secureplan.app.feature.calculations.Ssf130Calculator
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import java.util.zip.ZipOutputStream
import javax.inject.Inject
import javax.inject.Singleton

data class ExportBundle(
    val project: Project,
    val drawings: List<Drawing>,
    val placements: Map<String, List<SymbolPlacement>>,
    val products: Map<String, Product>,
    val calculations: List<Calculation>,
    val photos: List<GeoPhoto>,
    val protocolInstances: List<ProtocolInstance>,
    val protocolTemplates: Map<String, ProtocolTemplate>,
    val ssf130Result: Ssf130Calculator.Ssf130Result?
)

@Singleton
class ExportManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val projectRepository: ProjectRepository,
    private val drawingRepository: DrawingRepository,
    private val symbolPlacementRepository: SymbolPlacementRepository,
    private val productRepository: ProductRepository,
    private val calculationRepository: CalculationRepository,
    private val geoPhotoRepository: GeoPhotoRepository,
    private val protocolInstanceRepository: ProtocolInstanceRepository,
    private val protocolTemplateRepository: ProtocolTemplateRepository
) {
    private val gson = Gson()

    suspend fun buildBundle(projectId: String): ExportBundle {
        val project = projectRepository.getProjectById(projectId).first()
            ?: throw IllegalArgumentException("Project not found")
        val drawings = drawingRepository.getDrawingsForProject(projectId).first()
        val allProducts = productRepository.getAllProducts().first().associateBy { it.id }
        val calculations = calculationRepository.getCalculationsForProject(projectId).first()
        val photos = geoPhotoRepository.getPhotosForProject(projectId).first()
        val protocolInstances = protocolInstanceRepository.getInstancesForProject(projectId).first()
        val allTemplates = protocolTemplateRepository.getAllTemplates().first().associateBy { it.id }

        val placementsMap = mutableMapOf<String, List<SymbolPlacement>>()
        for (drawing in drawings) {
            val placements = symbolPlacementRepository.getPlacementsForDrawing(drawing.id).first()
            placementsMap[drawing.id] = placements
        }

        val componentLoads = placementsMap.values.flatten()
            .groupBy { it.productId }
            .mapNotNull { (productId, placements) ->
                val product = allProducts[productId ?: return@mapNotNull null] ?: return@mapNotNull null
                if (product.powerStandbyMa > 0 || product.powerAlarmMa > 0) {
                    Ssf130Calculator.ComponentLoad(
                        name = product.name,
                        count = placements.size,
                        standbyMaEach = product.powerStandbyMa,
                        alarmMaEach = product.powerAlarmMa,
                        voltageV = product.voltageV
                    )
                } else null
            }
        val ssf130Result = if (componentLoads.isNotEmpty()) Ssf130Calculator.calculate(componentLoads) else null

        return ExportBundle(
            project = project,
            drawings = drawings,
            placements = placementsMap,
            products = allProducts,
            calculations = calculations,
            photos = photos,
            protocolInstances = protocolInstances,
            protocolTemplates = allTemplates,
            ssf130Result = ssf130Result
        )
    }

    suspend fun exportPdf(bundle: ExportBundle, outputFile: File): File {
        PdfExporter(context).export(bundle, outputFile)
        return outputFile
    }

    suspend fun exportExcel(bundle: ExportBundle, outputFile: File): File {
        ExcelExporter().export(bundle, outputFile)
        return outputFile
    }

    suspend fun exportZip(bundle: ExportBundle, outputDir: File): File {
        val projectSlug = bundle.project.name.replace("[^a-zA-Z0-9]".toRegex(), "_")
        val zipFile = File(outputDir, "${projectSlug}_export.zip")
        ZipOutputStream(FileOutputStream(zipFile)).use { zos ->
            val pdfFile = File(outputDir, "${projectSlug}.pdf")
            PdfExporter(context).export(bundle, pdfFile)
            addFileToZip(zos, pdfFile, "rapport.pdf")
            pdfFile.delete()

            val excelFile = File(outputDir, "${projectSlug}.xlsx")
            ExcelExporter().export(bundle, excelFile)
            addFileToZip(zos, excelFile, "strombudget.xlsx")
            excelFile.delete()

            for (photo in bundle.photos) {
                val photoFile = File(photo.photoUri)
                if (photoFile.exists()) {
                    addFileToZip(zos, photoFile, "images/${photoFile.name}")
                }
            }
        }
        return zipFile
    }

    suspend fun exportSecurePlan(bundle: ExportBundle, outputFile: File): File {
        ZipOutputStream(FileOutputStream(outputFile)).use { zos ->
            val manifest = mapOf(
                "version" to 1,
                "exportedAt" to java.time.Instant.now().toString(),
                "appVersion" to "1.0.0"
            )
            addStringToZip(zos, gson.toJson(manifest), "manifest.json")

            val projectData = mapOf(
                "project" to bundle.project,
                "drawings" to bundle.drawings,
                "calculations" to bundle.calculations,
                "protocolInstances" to bundle.protocolInstances
            )
            addStringToZip(zos, gson.toJson(projectData), "project.json")

            for (photo in bundle.photos) {
                val photoFile = File(photo.photoUri)
                if (photoFile.exists()) {
                    addFileToZip(zos, photoFile, "images/${photoFile.name}")
                }
            }
        }
        return outputFile
    }

    suspend fun importSecurePlan(securePlanFile: File) {
        ZipInputStream(FileInputStream(securePlanFile)).use { zis ->
            var entry = zis.nextEntry
            while (entry != null) {
                if (entry.name == "project.json") {
                    // Full import would deserialize and save all entities
                    @Suppress("UNUSED_VARIABLE")
                    val content = zis.readBytes().toString(Charsets.UTF_8)
                }
                zis.closeEntry()
                entry = zis.nextEntry
            }
        }
    }

    private fun addFileToZip(zos: ZipOutputStream, file: File, entryName: String) {
        zos.putNextEntry(ZipEntry(entryName))
        FileInputStream(file).use { it.copyTo(zos) }
        zos.closeEntry()
    }

    private fun addStringToZip(zos: ZipOutputStream, content: String, entryName: String) {
        zos.putNextEntry(ZipEntry(entryName))
        zos.write(content.toByteArray(Charsets.UTF_8))
        zos.closeEntry()
    }
}
