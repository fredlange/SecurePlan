package se.secureplan.app.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "symbols")
data class SymbolEntity(
    @PrimaryKey val id: String,
    val name: String,
    val category: String,              // maps to SystemCategory
    val svgData: String?,              // inline SVG or path data
    val iconResName: String?,          // drawable resource name
    val color: Long,                   // ARGB packed as Long
    val isCustom: Boolean
)
