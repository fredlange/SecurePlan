package se.secureplan.app.feature.export

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import se.secureplan.app.core.domain.model.ProtocolInstance
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class PdfExporter(private val context: Context) {

    companion object {
        private const val PAGE_WIDTH = 595
        private const val PAGE_HEIGHT = 842
        private const val MARGIN = 50f
        private const val LINE_HEIGHT = 20f
    }

    fun export(bundle: ExportBundle, file: File) {
        val document = PdfDocument()
        try {
            var pageNumber = 1
            addCoverPage(document, bundle, pageNumber++)
            addComponentListPage(document, bundle, pageNumber++)
            addCalculationsPage(document, bundle, pageNumber++)
            bundle.protocolInstances
                .filter { it.status == "COMPLETED" }
                .forEach { instance ->
                    addProtocolPage(document, bundle, instance, pageNumber++)
                }
            FileOutputStream(file).use { document.writeTo(it) }
        } finally {
            document.close()
        }
    }

    private fun addCoverPage(document: PdfDocument, bundle: ExportBundle, pageNum: Int) {
        val pageInfo = PdfDocument.PageInfo.Builder(PAGE_WIDTH, PAGE_HEIGHT, pageNum).create()
        val page = document.startPage(pageInfo)
        val canvas = page.canvas
        val project = bundle.project
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        val titlePaint = Paint().apply { textSize = 28f; color = Color.rgb(25, 118, 210); isFakeBoldText = true }
        val bodyPaint = Paint().apply { textSize = 14f; color = Color.DKGRAY }
        val labelPaint = Paint().apply { textSize = 11f; color = Color.GRAY }
        val footerPaint = Paint().apply { textSize = 10f; color = Color.GRAY }
        val headerBarPaint = Paint().apply { color = Color.rgb(25, 118, 210) }

        canvas.drawRect(0f, 0f, PAGE_WIDTH.toFloat(), 8f, headerBarPaint)

        var y = 100f
        canvas.drawText("PROJEKTDOKUMENTATION", MARGIN, y, labelPaint)
        y += 40f
        canvas.drawText(project.name, MARGIN, y, titlePaint)
        y += 40f

        val divPaint = Paint().apply { color = Color.rgb(200, 200, 200); strokeWidth = 1f }
        canvas.drawLine(MARGIN, y, PAGE_WIDTH - MARGIN, y, divPaint)
        y += 24f

        listOf(
            "Kund" to project.clientName,
            "Adress" to project.address,
            "Installatör" to "${project.installerName} · ${project.installerCompany}",
            "Datum" to dateFormat.format(Date(project.createdAt)),
            "System" to project.systemCategory.name
        ).forEach { (label, value) ->
            canvas.drawText(label.uppercase(), MARGIN, y, labelPaint)
            canvas.drawText(value, MARGIN + 120f, y, bodyPaint)
            y += LINE_HEIGHT + 4f
        }

        canvas.drawText("Framtaget av SecurePlan", MARGIN, (PAGE_HEIGHT - 30).toFloat(), footerPaint)
        canvas.drawText("Sida $pageNum", (PAGE_WIDTH - 80).toFloat(), (PAGE_HEIGHT - 30).toFloat(), footerPaint)
        document.finishPage(page)
    }

    private fun addComponentListPage(document: PdfDocument, bundle: ExportBundle, pageNum: Int) {
        val pageInfo = PdfDocument.PageInfo.Builder(PAGE_WIDTH, PAGE_HEIGHT, pageNum).create()
        val page = document.startPage(pageInfo)
        val canvas = page.canvas

        val titlePaint = Paint().apply { textSize = 18f; color = Color.rgb(25, 118, 210); isFakeBoldText = true }
        val headerPaint = Paint().apply { textSize = 10f; color = Color.WHITE; isFakeBoldText = true }
        val rowPaint = Paint().apply { textSize = 10f; color = Color.DKGRAY }
        val bgPaint = Paint().apply { color = Color.rgb(25, 118, 210) }
        val altBgPaint = Paint().apply { color = Color.rgb(240, 245, 255) }
        val footerPaint = Paint().apply { textSize = 10f; color = Color.GRAY }

        var y = MARGIN
        canvas.drawText("Komponentlista", MARGIN, y, titlePaint)
        y += 30f

        canvas.drawRect(MARGIN, y - 14f, PAGE_WIDTH - MARGIN, y + 4f, bgPaint)
        val cols = listOf(MARGIN, MARGIN + 140f, MARGIN + 260f, MARGIN + 330f, MARGIN + 380f, MARGIN + 430f)
        val headers = listOf("Komponent", "Fabrikat", "Artnr", "Antal", "Vila mA", "Larm mA")
        headers.forEachIndexed { i, h -> canvas.drawText(h, cols[i], y, headerPaint) }
        y += 18f

        var row = 0
        bundle.placements.values.flatten()
            .groupBy { it.productId }
            .forEach { (productId, placements) ->
                val product = bundle.products[productId ?: return@forEach] ?: return@forEach
                if (row % 2 == 1) {
                    canvas.drawRect(MARGIN, y - 12f, PAGE_WIDTH - MARGIN, y + 4f, altBgPaint)
                }
                val data = listOf(
                    product.name.take(22),
                    product.manufacturer.take(18),
                    product.articleNumber.take(10),
                    placements.size.toString(),
                    product.powerStandbyMa.toInt().toString(),
                    product.powerAlarmMa.toInt().toString()
                )
                data.forEachIndexed { i, d -> canvas.drawText(d, cols[i], y, rowPaint) }
                y += LINE_HEIGHT
                row++
                if (y > PAGE_HEIGHT - MARGIN) return@forEach
            }

        canvas.drawText("Sida $pageNum", (PAGE_WIDTH - 80).toFloat(), (PAGE_HEIGHT - 30).toFloat(), footerPaint)
        document.finishPage(page)
    }

    private fun addCalculationsPage(document: PdfDocument, bundle: ExportBundle, pageNum: Int) {
        val pageInfo = PdfDocument.PageInfo.Builder(PAGE_WIDTH, PAGE_HEIGHT, pageNum).create()
        val page = document.startPage(pageInfo)
        val canvas = page.canvas

        val titlePaint = Paint().apply { textSize = 18f; color = Color.rgb(25, 118, 210); isFakeBoldText = true }
        val bodyPaint = Paint().apply { textSize = 11f; color = Color.DKGRAY }
        val boldPaint = Paint().apply { textSize = 12f; color = Color.BLACK; isFakeBoldText = true }
        val labelPaint = Paint().apply { textSize = 10f; color = Color.GRAY }
        val bgPaint = Paint().apply { color = Color.rgb(25, 118, 210) }
        val headerPaint = Paint().apply { textSize = 10f; color = Color.WHITE; isFakeBoldText = true }
        val rowAltPaint = Paint().apply { color = Color.rgb(240, 245, 255) }
        val footerPaint = Paint().apply { textSize = 10f; color = Color.GRAY }

        var y = MARGIN
        canvas.drawText("Strömbudget SSF 130", MARGIN, y, titlePaint)
        y += 30f

        val result = bundle.ssf130Result
        if (result == null) {
            canvas.drawText("Inga komponenter med strömdata hittades.", MARGIN, y, bodyPaint)
        } else {
            canvas.drawRect(MARGIN, y - 14f, PAGE_WIDTH - MARGIN, y + 4f, bgPaint)
            val cols = listOf(MARGIN, MARGIN + 140f, MARGIN + 200f, MARGIN + 260f, MARGIN + 330f, MARGIN + 400f)
            val headers = listOf("Komponent", "Antal", "Vila/st", "Larm/st", "Σ Vila", "Σ Larm")
            headers.forEachIndexed { i, h -> canvas.drawText(h, cols[i], y, headerPaint) }
            y += 18f

            result.componentLoads.forEachIndexed { idx, comp ->
                if (idx % 2 == 1) canvas.drawRect(MARGIN, y - 12f, PAGE_WIDTH - MARGIN, y + 4f, rowAltPaint)
                val data = listOf(
                    comp.name.take(22),
                    comp.count.toString(),
                    "${comp.standbyMaEach.toInt()} mA",
                    "${comp.alarmMaEach.toInt()} mA",
                    "${(comp.standbyMaEach * comp.count).toInt()} mA",
                    "${(comp.alarmMaEach * comp.count).toInt()} mA"
                )
                data.forEachIndexed { i, d -> canvas.drawText(d, cols[i], y, bodyPaint) }
                y += LINE_HEIGHT
            }

            y += 12f
            val divPaint = Paint().apply { color = Color.LTGRAY; strokeWidth = 1f }
            canvas.drawLine(MARGIN, y, PAGE_WIDTH - MARGIN, y, divPaint)
            y += 20f

            fun summaryRow(label: String, value: String, bold: Boolean = false) {
                canvas.drawText(label, MARGIN, y, labelPaint)
                canvas.drawText(value, MARGIN + 250f, y, if (bold) boldPaint else bodyPaint)
                y += LINE_HEIGHT
            }
            summaryRow("Total vilström:", "${result.totalStandbyMa.toInt()} mA")
            summaryRow("Total larmström:", "${result.totalAlarmMa.toInt()} mA")
            summaryRow("Batteri Alt 1 (60h+30min):", "${"%.1f".format(result.batteryCapacityAh_Alt1)} Ah")
            summaryRow("Batteri Alt 2 (12h+3min):", "${"%.1f".format(result.batteryCapacityAh_Alt2)} Ah")
            summaryRow("Rekommenderat batteri:", "${"%.1f".format(result.recommendedBatteryAh)} Ah", bold = true)
            summaryRow("Nätdel:", "${"%.2f".format(result.powerSupplyCurrentA)} A / ${result.powerSupplyVoltage} V")
        }

        canvas.drawText("Sida $pageNum", (PAGE_WIDTH - 80).toFloat(), (PAGE_HEIGHT - 30).toFloat(), footerPaint)
        document.finishPage(page)
    }

    private fun addProtocolPage(
        document: PdfDocument,
        bundle: ExportBundle,
        instance: ProtocolInstance,
        pageNum: Int
    ) {
        val pageInfo = PdfDocument.PageInfo.Builder(PAGE_WIDTH, PAGE_HEIGHT, pageNum).create()
        val page = document.startPage(pageInfo)
        val canvas = page.canvas

        val titlePaint = Paint().apply { textSize = 16f; color = Color.rgb(25, 118, 210); isFakeBoldText = true }
        val bodyPaint = Paint().apply { textSize = 11f; color = Color.DKGRAY }
        val labelPaint = Paint().apply { textSize = 9f; color = Color.GRAY }
        val greenPaint = Paint().apply { textSize = 11f; color = Color.rgb(46, 125, 50) }
        val footerPaint = Paint().apply { textSize = 10f; color = Color.GRAY }

        var y = MARGIN
        val templateName = bundle.protocolTemplates[instance.templateId]?.name ?: "Protokoll"
        canvas.drawText(templateName, MARGIN, y, titlePaint)
        y += 20f
        canvas.drawText(
            "Status: ${if (instance.status == "COMPLETED") "Signerad" else "Utkast"}",
            MARGIN, y, if (instance.status == "COMPLETED") greenPaint else bodyPaint
        )
        y += 24f

        try {
            val values: Map<String, String> = Gson().fromJson(
                instance.valuesJson,
                object : TypeToken<Map<String, String>>() {}.type
            ) ?: emptyMap()
            val template = bundle.protocolTemplates[instance.templateId]
            val fields: List<Map<String, Any>> = try {
                Gson().fromJson(
                    template?.fieldsJson ?: "[]",
                    object : TypeToken<List<Map<String, Any>>>() {}.type
                )
            } catch (e: Exception) {
                emptyList()
            }

            fields.forEach { field ->
                val fieldId = field["id"]?.toString() ?: return@forEach
                val label = field["label"]?.toString() ?: fieldId
                val value = values[fieldId] ?: ""
                if (value.isNotBlank() && !fieldId.startsWith("_")) {
                    canvas.drawText(label.uppercase(), MARGIN, y, labelPaint)
                    y += 14f
                    canvas.drawText(value.take(80), MARGIN, y, bodyPaint)
                    y += LINE_HEIGHT
                }
                if (y > PAGE_HEIGHT - MARGIN) return@forEach
            }

            instance.signedBy?.let {
                y += 8f
                canvas.drawText("TEKNIKERN", MARGIN, y, labelPaint)
                y += 14f
                canvas.drawText(it, MARGIN, y, bodyPaint)
            }
        } catch (e: Exception) {
            canvas.drawText("Fel vid rendering av protokoll.", MARGIN, y, bodyPaint)
        }

        canvas.drawText("Sida $pageNum", (PAGE_WIDTH - 80).toFloat(), (PAGE_HEIGHT - 30).toFloat(), footerPaint)
        document.finishPage(page)
    }
}
