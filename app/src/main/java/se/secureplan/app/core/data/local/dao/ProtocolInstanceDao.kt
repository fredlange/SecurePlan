package se.secureplan.app.core.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import se.secureplan.app.core.data.local.entity.ProtocolInstanceEntity

@Dao
interface ProtocolInstanceDao {
    @Query("SELECT * FROM protocol_instances WHERE projectId = :projectId ORDER BY createdAt DESC")
    fun getInstancesForProject(projectId: String): Flow<List<ProtocolInstanceEntity>>

    @Query("SELECT * FROM protocol_instances WHERE projectId = :projectId AND status = :status ORDER BY updatedAt DESC")
    fun getInstancesByStatus(projectId: String, status: String): Flow<List<ProtocolInstanceEntity>>

    @Query("SELECT * FROM protocol_instances WHERE id = :id")
    fun getInstanceById(id: String): Flow<ProtocolInstanceEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInstance(instance: ProtocolInstanceEntity)

    @Update
    suspend fun updateInstance(instance: ProtocolInstanceEntity)

    @Delete
    suspend fun deleteInstance(instance: ProtocolInstanceEntity)

    @Query("DELETE FROM protocol_instances WHERE id = :id")
    suspend fun deleteInstanceById(id: String)
}
