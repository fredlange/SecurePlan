package se.secureplan.app.core.domain.model

enum class SystemCategory { INTRUSION, FIRE, ACCESS, CCTV, INTERCOM }

enum class ProjectStatus { PLANNING, ACTIVE, REVIEW, COMPLETED, ARCHIVED }

enum class LayerType { POWER, DATA, DETECTION, CONTROL, STRUCTURE }

data class Project(
    val id: String,
    val name: String,
    val clientName: String,
    val address: String,
    val description: String,
    val systemCategory: SystemCategory,
    val status: ProjectStatus,
    val createdAt: Long,
    val updatedAt: Long,
    val installerName: String,
    val installerCompany: String,
    val installerEmail: String,
    val installerPhone: String,
    val coverImageUri: String?
)
