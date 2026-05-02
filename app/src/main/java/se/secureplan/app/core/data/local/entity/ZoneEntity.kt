package se.secureplan.app.core.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "zones",
    foreignKeys = [
        ForeignKey(
            entity = DrawingEntity::class,
            parentColumns = ["id"],
            childColumns = ["drawingId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("drawingId")]
)
data class ZoneEntity(
    @PrimaryKey val id: String,
    val drawingId: String,
    val name: String,
    val zoneNumber: Int,
    val polygonJson: String,           // JSON array of {x, y} normalized vertices
    val fillColorHex: String,
    val fillAlpha: Float,              // 0.0–1.0
    val strokeColorHex: String,
    val notes: String,
    val isVisible: Boolean,
    val createdAt: Long
)
