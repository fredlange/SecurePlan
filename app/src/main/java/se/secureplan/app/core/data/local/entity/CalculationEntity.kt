package se.secureplan.app.core.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "calculations",
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
data class CalculationEntity(
    @PrimaryKey val id: String,
    val projectId: String,
    val title: String,
    val type: String,                  // CABLE_LENGTH, BATTERY_BACKUP, POWER_CONSUMPTION, ZONE_AREA
    val inputsJson: String,            // JSON map of input parameters
    val result: Double,
    val unit: String,                  // m, Ah, W, m²
    val notes: String,
    val createdAt: Long,
    val updatedAt: Long
)
