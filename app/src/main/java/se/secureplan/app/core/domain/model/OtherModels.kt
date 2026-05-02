package se.secureplan.app.core.domain.model

data class GeoPhoto(
    val id: String,
    val projectId: String,
    val drawingId: String?,
    val photoUri: String,
    val latitude: Double?,
    val longitude: Double?,
    val caption: String,
    val takenAt: Long,
    val xNorm: Float?,
    val yNorm: Float?
)

data class ProtocolTemplate(
    val id: String,
    val name: String,
    val systemCategory: String,
    val description: String,
    val fieldsJson: String,
    val version: Int,
    val isBuiltIn: Boolean,
    val createdAt: Long
)

data class ProtocolInstance(
    val id: String,
    val projectId: String,
    val templateId: String,
    val valuesJson: String,
    val status: String,
    val signedBy: String?,
    val signedAt: Long?,
    val createdAt: Long,
    val updatedAt: Long
)

data class Calculation(
    val id: String,
    val projectId: String,
    val title: String,
    val type: String,
    val inputsJson: String,
    val result: Double,
    val unit: String,
    val notes: String,
    val createdAt: Long,
    val updatedAt: Long
)
