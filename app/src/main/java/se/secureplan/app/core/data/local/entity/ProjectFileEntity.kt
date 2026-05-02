package se.secureplan.app.core.data.local.entity

import androidx.room.*

@Entity(
    tableName = "project_files",
    foreignKeys = [
        ForeignKey(
            entity = ProjectEntity::class,
            parentColumns = ["id"],
            childColumns = ["projectId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("projectId")]
)
data class ProjectFileEntity(
    @PrimaryKey val id: String,
    val projectId: String,
    val name: String,
    val mimeType: String,
    val filePath: String,
    val sizeBytes: Long,
    val addedAt: Long,
    val notes: String
)
