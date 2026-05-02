package se.secureplan.app.core.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "symbol_placements",
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
data class SymbolPlacementEntity(
    @PrimaryKey val id: String,
    val drawingId: String,
    val symbolId: String,
    val productId: String?,
    val xNorm: Float,                  // normalized 0.0–1.0 x coordinate
    val yNorm: Float,                  // normalized 0.0–1.0 y coordinate
    val rotation: Float,               // degrees
    val label: String,
    val notes: String,
    val layerType: String,             // LayerType enum name
    val isVisible: Boolean,
    val createdAt: Long
)
