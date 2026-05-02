package se.secureplan.app.core.data.repository.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import se.secureplan.app.core.data.local.dao.*
import se.secureplan.app.core.data.mapper.*
import se.secureplan.app.core.domain.model.*
import se.secureplan.app.core.domain.repository.*
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val dao: ProductDao
) : ProductRepository {
    override fun getAllProducts() = dao.getAllProducts().map { it.map { e -> e.toDomain() } }
    override fun getProductsByCategory(category: String) =
        dao.getProductsByCategory(category).map { it.map { e -> e.toDomain() } }
    override fun searchProducts(query: String) =
        dao.searchProducts(query).map { it.map { e -> e.toDomain() } }
    override suspend fun getProductCount() = dao.getProductCount()
    override suspend fun saveProduct(product: Product) = dao.insertProduct(product.toEntity())
    override suspend fun deleteProduct(id: String) {
        dao.getProductById(id)?.let { dao.deleteProduct(it) }
    }
}

class SymbolRepositoryImpl @Inject constructor(
    private val dao: SymbolDao
) : SymbolRepository {
    override fun getAllSymbols() = dao.getAllSymbols().map { it.map { e -> e.toDomain() } }
    override fun getSymbolsByCategory(category: String) =
        dao.getSymbolsByCategory(category).map { it.map { e -> e.toDomain() } }
    override suspend fun saveSymbol(symbol: Symbol) = dao.insertSymbol(symbol.toEntity())
    override suspend fun deleteSymbol(id: String) {
        dao.getSymbolById(id)?.let { dao.deleteSymbol(it) }
    }
    override suspend fun getSymbolCount() = dao.getSymbolCount()
}

class GeoPhotoRepositoryImpl @Inject constructor(
    private val dao: GeoPhotoDao
) : GeoPhotoRepository {
    override fun getPhotosForProject(projectId: String): Flow<List<GeoPhoto>> =
        dao.getPhotosForProject(projectId).map { it.map { e -> e.toDomain() } }
    override fun getPhotosForDrawing(drawingId: String): Flow<List<GeoPhoto>> =
        dao.getPhotosForDrawing(drawingId).map { it.map { e -> e.toDomain() } }
    override suspend fun savePhoto(photo: GeoPhoto) = dao.insertPhoto(photo.toEntity())
    override suspend fun deletePhoto(id: String) = dao.deletePhotoById(id)
}

class ProtocolTemplateRepositoryImpl @Inject constructor(
    private val dao: ProtocolTemplateDao
) : ProtocolTemplateRepository {
    override fun getAllTemplates() = dao.getAllTemplates().map { it.map { e -> e.toDomain() } }
    override fun getTemplatesByCategory(category: String) =
        dao.getTemplatesByCategory(category).map { it.map { e -> e.toDomain() } }
    override suspend fun saveTemplate(template: ProtocolTemplate) = dao.insertTemplate(template.toEntity())
    override suspend fun getTemplateCount() = dao.getTemplateCount()
}

class ProtocolInstanceRepositoryImpl @Inject constructor(
    private val dao: ProtocolInstanceDao
) : ProtocolInstanceRepository {
    override fun getInstancesForProject(projectId: String): Flow<List<ProtocolInstance>> =
        dao.getInstancesForProject(projectId).map { it.map { e -> e.toDomain() } }
    override fun getInstanceById(id: String): Flow<ProtocolInstance?> =
        dao.getInstanceById(id).map { it?.toDomain() }
    override suspend fun saveInstance(instance: ProtocolInstance) = dao.insertInstance(instance.toEntity())
    override suspend fun deleteInstance(id: String) = dao.deleteInstanceById(id)
}

class CalculationRepositoryImpl @Inject constructor(
    private val dao: CalculationDao
) : CalculationRepository {
    override fun getCalculationsForProject(projectId: String): Flow<List<Calculation>> =
        dao.getCalculationsForProject(projectId).map { it.map { e -> e.toDomain() } }
    override suspend fun saveCalculation(calculation: Calculation) = dao.insertCalculation(calculation.toEntity())
    override suspend fun deleteCalculation(id: String) = dao.deleteCalculationById(id)
}
