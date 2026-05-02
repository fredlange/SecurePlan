package se.secureplan.app.core.data.repository.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import se.secureplan.app.core.data.local.dao.*
import se.secureplan.app.core.data.mapper.*
import se.secureplan.app.core.domain.model.*
import se.secureplan.app.core.domain.repository.*
import javax.inject.Inject

class DrawingRepositoryImpl @Inject constructor(
    private val dao: DrawingDao
) : DrawingRepository {
    override fun getDrawingsForProject(projectId: String) =
        dao.getDrawingsForProject(projectId).map { it.map { e -> e.toDomain() } }
    override fun getDrawingById(id: String) = dao.getDrawingById(id).map { it?.toDomain() }
    override suspend fun saveDrawing(drawing: Drawing) = dao.insertDrawing(drawing.toEntity())
    override suspend fun deleteDrawing(id: String) = dao.deleteDrawingById(id)
    override fun getDrawingCountForProject(projectId: String) = dao.getDrawingCountForProject(projectId)
}

class SymbolPlacementRepositoryImpl @Inject constructor(
    private val dao: SymbolPlacementDao
) : SymbolPlacementRepository {
    override fun getPlacementsForDrawing(drawingId: String): Flow<List<SymbolPlacement>> =
        dao.getPlacementsForDrawing(drawingId).map { it.map { e -> e.toDomain() } }
    override suspend fun savePlacement(placement: SymbolPlacement) =
        dao.insertPlacement(placement.toEntity())
    override suspend fun deletePlacement(id: String) = dao.deletePlacementById(id)
}

class CablePathRepositoryImpl @Inject constructor(
    private val dao: CablePathDao
) : CablePathRepository {
    override fun getCablePathsForDrawing(drawingId: String): Flow<List<CablePath>> =
        dao.getCablePathsForDrawing(drawingId).map { it.map { e -> e.toDomain() } }
    override suspend fun saveCablePath(path: CablePath) = dao.insertCablePath(path.toEntity())
    override suspend fun deleteCablePath(id: String) = dao.deleteCablePathById(id)
}

class ZoneRepositoryImpl @Inject constructor(
    private val dao: ZoneDao
) : ZoneRepository {
    override fun getZonesForDrawing(drawingId: String): Flow<List<Zone>> =
        dao.getZonesForDrawing(drawingId).map { it.map { e -> e.toDomain() } }
    override suspend fun saveZone(zone: Zone) = dao.insertZone(zone.toEntity())
    override suspend fun deleteZone(id: String) = dao.deleteZoneById(id)
    override suspend fun getNextZoneNumber(drawingId: String): Int =
        (dao.getMaxZoneNumber(drawingId) ?: 0) + 1
}
