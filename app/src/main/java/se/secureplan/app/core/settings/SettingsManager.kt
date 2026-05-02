package se.secureplan.app.core.settings

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val prefs = context.getSharedPreferences("secureplan_settings", Context.MODE_PRIVATE)

    var companyName: String
        get() = prefs.getString("company_name", "") ?: ""
        set(value) { prefs.edit().putString("company_name", value).apply() }

    var defaultTechnician: String
        get() = prefs.getString("default_technician", "") ?: ""
        set(value) { prefs.edit().putString("default_technician", value).apply() }

    var currency: String
        get() = prefs.getString("currency", "SEK") ?: "SEK"
        set(value) { prefs.edit().putString("currency", value).apply() }

    var isDarkMode: Boolean
        get() = prefs.getBoolean("dark_mode", false)
        set(value) { prefs.edit().putBoolean("dark_mode", value).apply() }
}
