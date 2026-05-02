package se.secureplan.app.core.domain.model

data class Product(
    val id: String,
    val name: String,
    val manufacturer: String,
    val articleNumber: String,
    val category: String,
    val description: String,
    val specSheetUri: String?,
    val imageUri: String?,
    val price: Double,
    val currency: String,
    val isTemplate: Boolean
)
