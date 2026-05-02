package se.secureplan.app.core.domain.model

data class SymbolPlacement(
    val id: String,
    val drawingId: String,
    val symbolId: String,
    val productId: String?,
    val xNorm: Float,
    val yNorm: Float,
    val rotation: Float,
    val label: String,
    val notes: String,
    val layerType: LayerType,
    val isVisible: Boolean,
    val createdAt: Long
)

data class CablePath(
    val id: String,
    val drawingId: String,
    val points: List<NormPoint>,
    val cableType: String,
    val colorHex: String,
    val strokeWidth: Float,
    val label: String,
    val notes: String,
    val layerType: LayerType,
    val isVisible: Boolean,
    val createdAt: Long
)

data class Zone(
    val id: String,
    val drawingId: String,
    val name: String,
    val zoneNumber: Int,
    val polygon: List<NormPoint>,
    val fillColorHex: String,
    val fillAlpha: Float,
    val strokeColorHex: String,
    val notes: String,
    val isVisible: Boolean,
    val createdAt: Long
)

/** Normalized coordinate in [0.0, 1.0] */
data class NormPoint(val x: Float, val y: Float)
