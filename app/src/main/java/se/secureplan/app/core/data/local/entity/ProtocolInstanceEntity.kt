package se.secureplan.app.core.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "protocol_instances",
    foreignKeys = [
        ForeignKey(
            entity = ProjectEntity::class,
            parentColumns = ["id"],
            childColumns = ["projectId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ProtocolTemplateEntity::class,
            parentColumns = ["id"],
            childColumns = ["templateId"],
            onDelete = ForeignKey.RESTRICT
        )
    ],
    indices = [Index("projectId"), Index("templateId")]
)
data class ProtocolInstanceEntity(
    @PrimaryKey val id: String,
    val projectId: String,
    val templateId: String,
    val valuesJson: String,            // JSON map of field-id → value
    val status: String,                // DRAFT, IN_PROGRESS, COMPLETED, SIGNED
    val signedBy: String?,
    val signedAt: Long?,
    val createdAt: Long,
    val updatedAt: Long
)
