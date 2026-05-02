package se.secureplan.app.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "protocol_templates")
data class ProtocolTemplateEntity(
    @PrimaryKey val id: String,
    val name: String,
    val systemCategory: String,
    val description: String,
    val fieldsJson: String,            // JSON schema defining protocol fields
    val version: Int,
    val isBuiltIn: Boolean,
    val createdAt: Long
)
