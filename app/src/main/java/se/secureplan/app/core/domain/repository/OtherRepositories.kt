package se.secureplan.app.core.domain.repository

import kotlinx.coroutines.flow.Flow
import se.secureplan.app.core.domain.model.*

interface ProductRepository {
    fun getAllProducts(): Flow<List<Product>>
    fun getProductsByCategory(category: String): Flow<List<Product>>
    fun searchProducts(query: String): Flow<List<Product>>
    suspend fun getProductCount(): Int
    suspend fun saveProduct(product: Product)
    suspend fun deleteProduct(id: String)
}

interface SymbolRepository {
    fun getAllSymbols(): Flow<List<Symbol>>
    fun getSymbolsByCategory(category: String): Flow<List<Symbol>>
    suspend fun saveSymbol(symbol: Symbol)
    suspend fun deleteSymbol(id: String)
    suspend fun getSymbolCount(): Int
}

interface GeoPhotoRepository {
    fun getPhotosForProject(projectId: String): Flow<List<GeoPhoto>>
    fun getPhotosForDrawing(drawingId: String): Flow<List<GeoPhoto>>
    suspend fun savePhoto(photo: GeoPhoto)
    suspend fun deletePhoto(id: String)
}

interface ProtocolTemplateRepository {
    fun getAllTemplates(): Flow<List<ProtocolTemplate>>
    fun getTemplatesByCategory(category: String): Flow<List<ProtocolTemplate>>
    suspend fun saveTemplate(template: ProtocolTemplate)
    suspend fun getTemplateCount(): Int
}

interface ProtocolInstanceRepository {
    fun getInstancesForProject(projectId: String): Flow<List<ProtocolInstance>>
    fun getInstanceById(id: String): Flow<ProtocolInstance?>
    suspend fun saveInstance(instance: ProtocolInstance)
    suspend fun deleteInstance(id: String)
}

interface CalculationRepository {
    fun getCalculationsForProject(projectId: String): Flow<List<Calculation>>
    suspend fun saveCalculation(calculation: Calculation)
    suspend fun deleteCalculation(id: String)
}

interface ProjectFileRepository {
    fun getFilesForProject(projectId: String): Flow<List<ProjectFile>>
    suspend fun saveFile(file: ProjectFile)
    suspend fun deleteFile(id: String)
}
