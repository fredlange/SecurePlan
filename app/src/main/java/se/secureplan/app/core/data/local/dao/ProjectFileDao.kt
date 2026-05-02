package se.secureplan.app.core.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import se.secureplan.app.core.data.local.entity.ProjectFileEntity

@Dao
interface ProjectFileDao {
    @Query("SELECT * FROM project_files WHERE projectId = :projectId ORDER BY addedAt DESC")
    fun getFilesForProject(projectId: String): Flow<List<ProjectFileEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFile(file: ProjectFileEntity)

    @Update
    suspend fun updateFile(file: ProjectFileEntity)

    @Query("DELETE FROM project_files WHERE id = :id")
    suspend fun deleteFileById(id: String)

    @Query("SELECT COUNT(*) FROM project_files WHERE projectId = :projectId")
    fun getFileCountForProject(projectId: String): Flow<Int>
}
