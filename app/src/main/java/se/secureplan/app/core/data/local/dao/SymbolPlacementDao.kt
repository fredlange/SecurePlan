package se.secureplan.app.core.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import se.secureplan.app.core.data.local.entity.SymbolPlacementEntity

@Dao
interface SymbolPlacementDao {
    @Query("SELECT * FROM symbol_placements WHERE drawingId = :drawingId ORDER BY createdAt ASC")
    fun getPlacementsForDrawing(drawingId: String): Flow<List<SymbolPlacementEntity>>

    @Query("SELECT * FROM symbol_placements WHERE drawingId = :drawingId AND layerType = :layerType ORDER BY createdAt ASC")
    fun getPlacementsForDrawingByLayer(drawingId: String, layerType: String): Flow<List<SymbolPlacementEntity>>

    @Query("SELECT * FROM symbol_placements WHERE id = :id")
    suspend fun getPlacementById(id: String): SymbolPlacementEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlacement(placement: SymbolPlacementEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlacements(placements: List<SymbolPlacementEntity>)

    @Update
    suspend fun updatePlacement(placement: SymbolPlacementEntity)

    @Delete
    suspend fun deletePlacement(placement: SymbolPlacementEntity)

    @Query("DELETE FROM symbol_placements WHERE id = :id")
    suspend fun deletePlacementById(id: String)

    @Query("SELECT COUNT(*) FROM symbol_placements WHERE drawingId = :drawingId")
    fun getPlacementCountForDrawing(drawingId: String): Flow<Int>
}
