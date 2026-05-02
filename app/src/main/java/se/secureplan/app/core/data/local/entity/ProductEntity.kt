package se.secureplan.app.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey val id: String,
    val name: String,
    val manufacturer: String,
    val articleNumber: String,
    val category: String,           // INTRUSION, FIRE, ACCESS, CCTV, INTERCOM, DOOR, OTHER
    val description: String,
    // Electrical specifications
    val powerStandbyMa: Float = 0f,
    val powerAlarmMa: Float = 0f,
    val voltageV: Float = 12f,
    val powerWatt: Float = 0f,
    // Physical specifications
    val widthMm: Float = 0f,
    val heightMm: Float = 0f,
    val depthMm: Float = 0f,
    val weightG: Float = 0f,
    val certifications: String = "",
    val specSheetUri: String? = null,
    val imageUri: String? = null,
    val price: Double = 0.0,
    val currency: String = "SEK",
    val isCustom: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)
