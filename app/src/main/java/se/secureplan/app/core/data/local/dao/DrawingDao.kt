package se.secureplan.app.core.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import se.secureplan.app.core.data.local.entity.DrawingEntity

@Dao
interface DrawingDao {
    @Query("SELECT * FROM drawings WHERE projectId = :projectId ORDER BY floor ASC, name ASC")
    fun getDrawingsForProject(projectId: String): Flow<List<DrawingEntity>>

    @Query("SELECT * FROM drawings WHERE id = :id")
    fun getDrawingById(id: String): Flow<DrawingEntity?>

    @Query("SELECT * FROM drawings WHERE id = :id")
    suspend fun getDrawingByIdOnce(id: String): DrawingEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDrawing(drawing: DrawingEntity)

    @Update
    suspend fun updateDrawing(drawing: DrawingEntity)

    @Delete
    suspend fun deleteDrawing(drawing: DrawingEntity)

    @Query("DELETE FROM drawings WHERE id = :id")
    suspend fun deleteDrawingById(id: String)

    @Query("SELECT COUNT(*) FROM drawings WHERE projectId = :projectId")
    fun getDrawingCountForProject(projectId: String): Flow<Int>
}
