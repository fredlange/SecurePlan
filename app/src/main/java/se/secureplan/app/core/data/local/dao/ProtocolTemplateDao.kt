package se.secureplan.app.core.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import se.secureplan.app.core.data.local.entity.ProtocolTemplateEntity

@Dao
interface ProtocolTemplateDao {
    @Query("SELECT * FROM protocol_templates ORDER BY name ASC")
    fun getAllTemplates(): Flow<List<ProtocolTemplateEntity>>

    @Query("SELECT * FROM protocol_templates WHERE systemCategory = :category ORDER BY name ASC")
    fun getTemplatesByCategory(category: String): Flow<List<ProtocolTemplateEntity>>

    @Query("SELECT * FROM protocol_templates WHERE isBuiltIn = 1 ORDER BY name ASC")
    fun getBuiltInTemplates(): Flow<List<ProtocolTemplateEntity>>

    @Query("SELECT * FROM protocol_templates WHERE id = :id")
    suspend fun getTemplateById(id: String): ProtocolTemplateEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTemplate(template: ProtocolTemplateEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTemplates(templates: List<ProtocolTemplateEntity>)

    @Update
    suspend fun updateTemplate(template: ProtocolTemplateEntity)

    @Delete
    suspend fun deleteTemplate(template: ProtocolTemplateEntity)

    @Query("SELECT COUNT(*) FROM protocol_templates")
    suspend fun getTemplateCount(): Int
}
