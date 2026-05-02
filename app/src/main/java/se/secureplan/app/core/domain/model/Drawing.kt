package se.secureplan.app.core.domain.model

data class Drawing(
    val id: String,
    val projectId: String,
    val name: String,
    val floor: Int,
    val backgroundUri: String?,
    val backgroundPageIndex: Int,
    val scaleMetersPerUnit: Float,
    val createdAt: Long,
    val updatedAt: Long,
    val width: Float,
    val height: Float
)
