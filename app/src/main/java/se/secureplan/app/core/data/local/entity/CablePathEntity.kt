package se.secureplan.app.core.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "cable_paths",
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
data class CablePathEntity(
    @PrimaryKey val id: String,
    val drawingId: String,
    val pointsJson: String,            // JSON array of {x, y} normalized points
    val cableType: String,             // e.g. "UTP Cat6", "Coax RG59"
    val colorHex: String,
    val strokeWidth: Float,
    val label: String,
    val notes: String,
    val layerType: String,
    val isVisible: Boolean,
    val createdAt: Long
)
