package se.secureplan.app.core.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import se.secureplan.app.core.data.local.entity.CablePathEntity

@Dao
interface CablePathDao {
    @Query("SELECT * FROM cable_paths WHERE drawingId = :drawingId ORDER BY createdAt ASC")
    fun getCablePathsForDrawing(drawingId: String): Flow<List<CablePathEntity>>

    @Query("SELECT * FROM cable_paths WHERE id = :id")
    suspend fun getCablePathById(id: String): CablePathEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCablePath(path: CablePathEntity)

    @Update
    suspend fun updateCablePath(path: CablePathEntity)

    @Delete
    suspend fun deleteCablePath(path: CablePathEntity)

    @Query("DELETE FROM cable_paths WHERE id = :id")
    suspend fun deleteCablePathById(id: String)

    @Query("SELECT COUNT(*) FROM cable_paths WHERE drawingId = :drawingId")
    fun getCablePathCountForDrawing(drawingId: String): Flow<Int>
}
