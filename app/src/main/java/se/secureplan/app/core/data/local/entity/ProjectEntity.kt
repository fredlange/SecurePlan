package se.secureplan.app.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "projects")
data class ProjectEntity(
    @PrimaryKey val id: String,
    val name: String,
    val clientName: String,
    val address: String,
    val description: String,
    val systemCategory: String,        // INTRUSION, FIRE, ACCESS, CCTV, INTERCOM
    val status: String,                // PLANNING, ACTIVE, REVIEW, COMPLETED, ARCHIVED
    val createdAt: Long,
    val updatedAt: Long,
    val installerName: String,
    val installerCompany: String,
    val installerEmail: String,
    val installerPhone: String,
    val coverImageUri: String?
)
