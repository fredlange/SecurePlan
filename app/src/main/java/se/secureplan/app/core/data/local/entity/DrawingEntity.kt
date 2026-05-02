package se.secureplan.app.core.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "drawings",
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
data class DrawingEntity(
    @PrimaryKey val id: String,
    val projectId: String,
    val name: String,
    val floor: Int,
    val backgroundUri: String?,        // URI to PDF page or image
    val backgroundPageIndex: Int,      // which PDF page
    val scaleMetersPerUnit: Float,     // drawing scale factor
    val createdAt: Long,
    val updatedAt: Long,
    val width: Float,                  // logical canvas width
    val height: Float                  // logical canvas height
)
