package se.secureplan.app.core.domain.repository

import kotlinx.coroutines.flow.Flow
import se.secureplan.app.core.domain.model.*

interface DrawingRepository {
    fun getDrawingsForProject(projectId: String): Flow<List<Drawing>>
    fun getDrawingById(id: String): Flow<Drawing?>
    suspend fun saveDrawing(drawing: Drawing)
    suspend fun deleteDrawing(id: String)
    fun getDrawingCountForProject(projectId: String): Flow<Int>
}

interface SymbolPlacementRepository {
    fun getPlacementsForDrawing(drawingId: String): Flow<List<SymbolPlacement>>
    fun getPlacementsForProject(projectId: String): Flow<List<SymbolPlacement>>
    suspend fun savePlacement(placement: SymbolPlacement)
    suspend fun deletePlacement(id: String)
}

interface CablePathRepository {
    fun getCablePathsForDrawing(drawingId: String): Flow<List<CablePath>>
    suspend fun saveCablePath(path: CablePath)
    suspend fun deleteCablePath(id: String)
}

interface ZoneRepository {
    fun getZonesForDrawing(drawingId: String): Flow<List<Zone>>
    suspend fun saveZone(zone: Zone)
    suspend fun deleteZone(id: String)
    suspend fun getNextZoneNumber(drawingId: String): Int
}
