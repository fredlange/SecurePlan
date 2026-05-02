package se.secureplan.app.core.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import se.secureplan.app.core.data.local.entity.ZoneEntity

@Dao
interface ZoneDao {
    @Query("SELECT * FROM zones WHERE drawingId = :drawingId ORDER BY zoneNumber ASC")
    fun getZonesForDrawing(drawingId: String): Flow<List<ZoneEntity>>

    @Query("SELECT * FROM zones WHERE id = :id")
    suspend fun getZoneById(id: String): ZoneEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertZone(zone: ZoneEntity)

    @Update
    suspend fun updateZone(zone: ZoneEntity)

    @Delete
    suspend fun deleteZone(zone: ZoneEntity)

    @Query("DELETE FROM zones WHERE id = :id")
    suspend fun deleteZoneById(id: String)

    @Query("SELECT MAX(zoneNumber) FROM zones WHERE drawingId = :drawingId")
    suspend fun getMaxZoneNumber(drawingId: String): Int?
}
