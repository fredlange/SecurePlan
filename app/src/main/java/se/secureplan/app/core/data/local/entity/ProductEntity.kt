package se.secureplan.app.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey val id: String,
    val name: String,
    val manufacturer: String,
    val articleNumber: String,
    val category: String,              // DETECTOR, KEYPAD, SIREN, CAMERA, etc.
    val description: String,
    val specSheetUri: String?,
    val imageUri: String?,
    val price: Double,
    val currency: String,
    val isTemplate: Boolean            // true = part of global product library
)
