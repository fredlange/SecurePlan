package se.secureplan.app.core.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import se.secureplan.app.core.data.local.entity.CalculationEntity

@Dao
interface CalculationDao {
    @Query("SELECT * FROM calculations WHERE projectId = :projectId ORDER BY createdAt DESC")
    fun getCalculationsForProject(projectId: String): Flow<List<CalculationEntity>>

    @Query("SELECT * FROM calculations WHERE projectId = :projectId AND type = :type ORDER BY createdAt DESC")
    fun getCalculationsByType(projectId: String, type: String): Flow<List<CalculationEntity>>

    @Query("SELECT * FROM calculations WHERE id = :id")
    suspend fun getCalculationById(id: String): CalculationEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCalculation(calculation: CalculationEntity)

    @Update
    suspend fun updateCalculation(calculation: CalculationEntity)

    @Delete
    suspend fun deleteCalculation(calculation: CalculationEntity)

    @Query("DELETE FROM calculations WHERE id = :id")
    suspend fun deleteCalculationById(id: String)

    @Query("SELECT COUNT(*) FROM calculations WHERE projectId = :projectId")
    fun getCalculationCountForProject(projectId: String): Flow<Int>
}
