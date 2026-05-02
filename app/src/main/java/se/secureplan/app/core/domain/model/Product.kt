package se.secureplan.app.core.domain.model

data class Product(
    val id: String,
    val name: String,
    val manufacturer: String,
    val articleNumber: String,
    val category: String,           // INTRUSION, FIRE, ACCESS, CCTV, INTERCOM, DOOR, OTHER
    val description: String,
    // Electrical specifications (for SSF 130 / SSF 1060 calculations)
    val powerStandbyMa: Float = 0f, // Standby current consumption in mA
    val powerAlarmMa: Float = 0f,   // Alarm/active current consumption in mA
    val voltageV: Float = 12f,      // Nominal operating voltage in V
    val powerWatt: Float = 0f,      // For CCTV/PoE: total power in Watts
    // Physical specifications
    val widthMm: Float = 0f,
    val heightMm: Float = 0f,
    val depthMm: Float = 0f,
    val weightG: Float = 0f,
    // Certifications (comma-separated, e.g. "SSF 1014, EN 50131-2")
    val certifications: String = "",
    val specSheetUri: String? = null,
    val imageUri: String? = null,
    val price: Double = 0.0,
    val currency: String = "SEK",
    val isCustom: Boolean = false,  // true = user-added, false = built-in library
    val createdAt: Long = System.currentTimeMillis()
)
