package se.secureplan.app.core.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "geo_photos",
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
data class GeoPhotoEntity(
    @PrimaryKey val id: String,
    val projectId: String,
    val drawingId: String?,            // optional link to a drawing
    val photoUri: String,
    val latitude: Double?,
    val longitude: Double?,
    val caption: String,
    val takenAt: Long,
    val xNorm: Float?,                 // pin position on drawing if linked
    val yNorm: Float?
)
