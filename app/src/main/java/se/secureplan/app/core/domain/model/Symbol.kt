package se.secureplan.app.core.domain.model

data class Symbol(
    val id: String,
    val name: String,
    val category: String,
    val svgData: String?,
    val iconResName: String?,
    val color: Long,
    val isCustom: Boolean
)
