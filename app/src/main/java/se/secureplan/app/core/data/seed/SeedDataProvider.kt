package se.secureplan.app.core.data.seed

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import se.secureplan.app.core.domain.model.Symbol
import se.secureplan.app.core.domain.model.ProtocolTemplate
import se.secureplan.app.core.domain.repository.SymbolRepository
import se.secureplan.app.core.domain.repository.ProtocolTemplateRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SeedDataProvider @Inject constructor(
    @ApplicationContext private val context: Context,
    private val symbolRepository: SymbolRepository,
    private val templateRepository: ProtocolTemplateRepository
) {

    suspend fun seedIfNeeded() {
        seedSymbols()
        seedProtocolTemplates()
    }

    private suspend fun seedSymbols() {
        if (symbolRepository.getSymbolCount() > 0) return

        val symbols = listOf(
            // ── Intrusion ──────────────────────────────────────────────────
            Symbol("sym_pir_ceiling",    "PIR Ceiling",        "INTRUSION", null, null, 0xFF1565C0, false),
            Symbol("sym_pir_corner",     "PIR Corner",         "INTRUSION", null, null, 0xFF1565C0, false),
            Symbol("sym_door_contact",   "Door Contact",       "INTRUSION", null, null, 0xFF1565C0, false),
            Symbol("sym_shock_sensor",   "Shock Sensor",       "INTRUSION", null, null, 0xFF1565C0, false),
            Symbol("sym_keypad",         "Keypad",             "INTRUSION", null, null, 0xFF1565C0, false),
            Symbol("sym_siren_indoor",   "Indoor Siren",       "INTRUSION", null, null, 0xFF1565C0, false),
            Symbol("sym_siren_outdoor",  "Outdoor Siren",      "INTRUSION", null, null, 0xFF1565C0, false),
            Symbol("sym_control_panel",  "Control Panel",      "INTRUSION", null, null, 0xFF1565C0, false),
            // ── Fire ──────────────────────────────────────────────────────
            Symbol("sym_smoke_detector", "Smoke Detector",     "FIRE",      null, null, 0xFFB71C1C, false),
            Symbol("sym_heat_detector",  "Heat Detector",      "FIRE",      null, null, 0xFFB71C1C, false),
            Symbol("sym_co_detector",    "CO Detector",        "FIRE",      null, null, 0xFFB71C1C, false),
            Symbol("sym_call_point",     "Manual Call Point",  "FIRE",      null, null, 0xFFB71C1C, false),
            Symbol("sym_fire_sounder",   "Fire Sounder",       "FIRE",      null, null, 0xFFB71C1C, false),
            Symbol("sym_fire_panel",     "Fire Panel",         "FIRE",      null, null, 0xFFB71C1C, false),
            // ── Access ────────────────────────────────────────────────────
            Symbol("sym_card_reader",    "Card Reader",        "ACCESS",    null, null, 0xFF1B5E20, false),
            Symbol("sym_door_lock",      "Electric Lock",      "ACCESS",    null, null, 0xFF1B5E20, false),
            Symbol("sym_access_ctrl",    "Access Controller",  "ACCESS",    null, null, 0xFF1B5E20, false),
            Symbol("sym_exit_button",    "Exit Button",        "ACCESS",    null, null, 0xFF1B5E20, false),
            // ── CCTV ──────────────────────────────────────────────────────
            Symbol("sym_cam_dome",       "Dome Camera",        "CCTV",      null, null, 0xFF4A148C, false),
            Symbol("sym_cam_bullet",     "Bullet Camera",      "CCTV",      null, null, 0xFF4A148C, false),
            Symbol("sym_cam_ptz",        "PTZ Camera",         "CCTV",      null, null, 0xFF4A148C, false),
            Symbol("sym_nvr",            "NVR / DVR",          "CCTV",      null, null, 0xFF4A148C, false),
            // ── Intercom ──────────────────────────────────────────────────
            Symbol("sym_door_station",   "Door Station",       "INTERCOM",  null, null, 0xFFE65100, false),
            Symbol("sym_indoor_unit",    "Indoor Monitor",     "INTERCOM",  null, null, 0xFFE65100, false),
            Symbol("sym_video_switch",   "Video Switch",       "INTERCOM",  null, null, 0xFFE65100, false)
        )
        symbols.forEach { symbolRepository.saveSymbol(it) }
    }

    private suspend fun seedProtocolTemplates() {
        if (templateRepository.getTemplateCount() > 0) return

        val now = System.currentTimeMillis()
        val templates = listOf(
            ProtocolTemplate(
                id = "tpl_intrusion_install",
                name = "Intrusion — Installation Protocol",
                systemCategory = "INTRUSION",
                description = "Commissioning checklist for burglar alarm installations per SS-EN 50131.",
                fieldsJson = """[
                  {"id":"customer","label":"Customer","type":"text","required":true},
                  {"id":"address","label":"Address","type":"text","required":true},
                  {"id":"grade","label":"Security Grade (1-4)","type":"number","required":true},
                  {"id":"detectors_tested","label":"All detectors walk-tested","type":"checkbox","required":true},
                  {"id":"sounder_tested","label":"Sounder/strobe tested","type":"checkbox","required":true},
                  {"id":"entry_exit_time","label":"Entry/Exit delay (s)","type":"number","required":false},
                  {"id":"notes","label":"Notes","type":"textarea","required":false}
                ]""",
                version = 1,
                isBuiltIn = true,
                createdAt = now
            ),
            ProtocolTemplate(
                id = "tpl_fire_commissioning",
                name = "Fire Alarm — Commissioning Protocol",
                systemCategory = "FIRE",
                description = "Commissioning and hand-over document per SS-EN 54.",
                fieldsJson = """[
                  {"id":"customer","label":"Customer","type":"text","required":true},
                  {"id":"address","label":"Address","type":"text","required":true},
                  {"id":"zones_count","label":"Number of zones","type":"number","required":true},
                  {"id":"detectors_count","label":"Number of detectors","type":"number","required":true},
                  {"id":"panel_tested","label":"Panel tested","type":"checkbox","required":true},
                  {"id":"evacuation_tested","label":"Evacuation alarm tested","type":"checkbox","required":true},
                  {"id":"installer_sign","label":"Installer signature","type":"text","required":true},
                  {"id":"notes","label":"Notes","type":"textarea","required":false}
                ]""",
                version = 1,
                isBuiltIn = true,
                createdAt = now
            ),
            ProtocolTemplate(
                id = "tpl_cctv_handover",
                name = "CCTV — System Hand-over",
                systemCategory = "CCTV",
                description = "Camera system hand-over checklist.",
                fieldsJson = """[
                  {"id":"customer","label":"Customer","type":"text","required":true},
                  {"id":"cameras_count","label":"Number of cameras","type":"number","required":true},
                  {"id":"recording_days","label":"Recording retention (days)","type":"number","required":true},
                  {"id":"remote_access","label":"Remote access configured","type":"checkbox","required":false},
                  {"id":"notes","label":"Notes","type":"textarea","required":false}
                ]""",
                version = 1,
                isBuiltIn = true,
                createdAt = now
            )
        )
        templates.forEach { templateRepository.saveTemplate(it) }
    }
}
