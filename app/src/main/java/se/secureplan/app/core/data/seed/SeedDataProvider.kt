package se.secureplan.app.core.data.seed

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import se.secureplan.app.core.domain.model.Product
import se.secureplan.app.core.domain.model.Symbol
import se.secureplan.app.core.domain.model.ProtocolTemplate
import se.secureplan.app.core.domain.repository.ProductRepository
import se.secureplan.app.core.domain.repository.SymbolRepository
import se.secureplan.app.core.domain.repository.ProtocolTemplateRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SeedDataProvider @Inject constructor(
    @ApplicationContext private val context: Context,
    private val symbolRepository: SymbolRepository,
    private val templateRepository: ProtocolTemplateRepository,
    private val productRepository: ProductRepository
) {

    suspend fun seedIfNeeded() {
        seedSymbols()
        seedProtocolTemplates()
        seedProducts()
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

    private suspend fun seedProducts() {
        if (productRepository.getProductCount() > 0) return

        val now = System.currentTimeMillis()
        val products = listOf(

            // ── INTRUSION ────────────────────────────────────────────────────
            Product(
                id = "prod_bosch_blue_pir",
                name = "Blue Line PIR-detektor",
                manufacturer = "Bosch",
                articleNumber = "DS150I",
                category = "INTRUSION",
                description = "Passiv infraröd rörelsedetektor för inomhusbruk. Täckningsvinkel 90°, räckvidd 12m.",
                powerStandbyMa = 9f, powerAlarmMa = 15f, voltageV = 12f,
                widthMm = 65f, heightMm = 102f, depthMm = 43f, weightG = 90f,
                certifications = "SSF 1014 Klass 2, EN 50131-2-2",
                price = 595.0, currency = "SEK", isCustom = false, createdAt = now
            ),
            Product(
                id = "prod_bosch_door_contact",
                name = "Magnetkontakt dörr/fönster",
                manufacturer = "Bosch",
                articleNumber = "ISC-M1L-W10",
                category = "INTRUSION",
                description = "Ytmonterad magnetkontakt för dörrar och fönster. Larmgap 15mm.",
                powerStandbyMa = 3f, powerAlarmMa = 5f, voltageV = 12f,
                widthMm = 28f, heightMm = 95f, depthMm = 18f, weightG = 40f,
                certifications = "EN 50131-2-6",
                price = 195.0, currency = "SEK", isCustom = false, createdAt = now
            ),
            Product(
                id = "prod_vanderbilt_keypad",
                name = "Koddosa SPC med display",
                manufacturer = "Vanderbilt",
                articleNumber = "SPCK520.100",
                category = "INTRUSION",
                description = "Koddosa med färgdisplay, RFID-läsare och beröringskänsliga knappar.",
                powerStandbyMa = 40f, powerAlarmMa = 60f, voltageV = 12f,
                widthMm = 130f, heightMm = 155f, depthMm = 23f, weightG = 250f,
                certifications = "EN 50131-3, SSF 1014 Klass 2",
                price = 2495.0, currency = "SEK", isCustom = false, createdAt = now
            ),
            Product(
                id = "prod_aritech_siren_indoor",
                name = "Inomhussiren med blixtljus",
                manufacturer = "Aritech",
                articleNumber = "SIR321",
                category = "INTRUSION",
                description = "Inomhussiren 110dB med vit LED-blixt. Sabotageskyddad.",
                powerStandbyMa = 5f, powerAlarmMa = 400f, voltageV = 12f,
                widthMm = 118f, heightMm = 118f, depthMm = 35f, weightG = 180f,
                certifications = "EN 50131-4",
                price = 695.0, currency = "SEK", isCustom = false, createdAt = now
            ),
            Product(
                id = "prod_dsc_central",
                name = "Larmcentral PowerSeries Pro",
                manufacturer = "DSC",
                articleNumber = "HS3032NK",
                category = "INTRUSION",
                description = "Larmcentral 32 zoner, inbyggt Ethernet, stöd för SSF 130 Klass 2-3.",
                powerStandbyMa = 100f, powerAlarmMa = 300f, voltageV = 12f,
                widthMm = 290f, heightMm = 390f, depthMm = 90f, weightG = 2800f,
                certifications = "SSF 1014 Klass 2-3, EN 50131-3, EN 50136-2",
                price = 8950.0, currency = "SEK", isCustom = false, createdAt = now
            ),
            Product(
                id = "prod_bosch_siren_outdoor",
                name = "Utomhussiren DS150i",
                manufacturer = "Bosch",
                articleNumber = "DS150I-EXT",
                category = "INTRUSION",
                description = "Utomhussiren 120dB med kraftigt blixtljus, IP54, värmeelement.",
                powerStandbyMa = 8f, powerAlarmMa = 600f, voltageV = 12f,
                widthMm = 195f, heightMm = 195f, depthMm = 80f, weightG = 800f,
                certifications = "EN 50131-4, IP54",
                price = 1595.0, currency = "SEK", isCustom = false, createdAt = now
            ),

            // ── FIRE ─────────────────────────────────────────────────────────
            Product(
                id = "prod_siemens_smoke_op",
                name = "Optisk rökdetektor",
                manufacturer = "Siemens",
                articleNumber = "OP121",
                category = "FIRE",
                description = "Optisk rökdetektor för brandlarm per EN 54-7. Konventionell.",
                powerStandbyMa = 0.1f, powerAlarmMa = 30f, voltageV = 24f,
                widthMm = 103f, heightMm = 50f, depthMm = 103f, weightG = 120f,
                certifications = "EN 54-7, SP-godkänd",
                price = 495.0, currency = "SEK", isCustom = false, createdAt = now
            ),
            Product(
                id = "prod_siemens_heat_det",
                name = "Värmedetektor 58°C",
                manufacturer = "Siemens",
                articleNumber = "HI121",
                category = "FIRE",
                description = "Konventionell värmedetektor, fast temperatur 58°C.",
                powerStandbyMa = 0.1f, powerAlarmMa = 25f, voltageV = 24f,
                widthMm = 103f, heightMm = 50f, depthMm = 103f, weightG = 110f,
                certifications = "EN 54-5, SP-godkänd",
                price = 395.0, currency = "SEK", isCustom = false, createdAt = now
            ),
            Product(
                id = "prod_siemens_call_point",
                name = "Manuellt larmdon (handlarmknapp)",
                manufacturer = "Siemens",
                articleNumber = "MS401",
                category = "FIRE",
                description = "Manuellt larmdon med brottnyckel. Röd, ytmontage.",
                powerStandbyMa = 0.05f, powerAlarmMa = 15f, voltageV = 24f,
                widthMm = 86f, heightMm = 86f, depthMm = 40f, weightG = 150f,
                certifications = "EN 54-11, SP-godkänd",
                price = 545.0, currency = "SEK", isCustom = false, createdAt = now
            ),
            Product(
                id = "prod_siemens_fire_panel",
                name = "Brandlarmcentral Cerberus",
                manufacturer = "Siemens",
                articleNumber = "FC922",
                category = "FIRE",
                description = "Adresserbar brandlarmcentral 1-2 slingor, LCD-display, inbyggd skrivare.",
                powerStandbyMa = 180f, powerAlarmMa = 500f, voltageV = 24f,
                widthMm = 395f, heightMm = 450f, depthMm = 120f, weightG = 8500f,
                certifications = "EN 54-2, EN 54-4, SP-godkänd",
                price = 24500.0, currency = "SEK", isCustom = false, createdAt = now
            ),

            // ── ACCESS ───────────────────────────────────────────────────────
            Product(
                id = "prod_assa_card_reader",
                name = "RFID-läsare ARX",
                manufacturer = "ASSA Abloy",
                articleNumber = "ARX-R10",
                category = "ACCESS",
                description = "Passerkortläsare MIFARE/DESFire, IP55, bakgrundsbelysning.",
                powerStandbyMa = 80f, powerAlarmMa = 120f, voltageV = 12f,
                widthMm = 73f, heightMm = 122f, depthMm = 22f, weightG = 160f,
                certifications = "EN 60839-11-1, IP55",
                price = 1895.0, currency = "SEK", isCustom = false, createdAt = now
            ),
            Product(
                id = "prod_abloy_electric_lock",
                name = "Elhållarmagnet EL460",
                manufacturer = "Abloy",
                articleNumber = "EL460",
                category = "ACCESS",
                description = "Elektromagnetisk dörrhållare 300N, ytmontage, med 12V/24V drift.",
                powerStandbyMa = 10f, powerAlarmMa = 500f, voltageV = 12f,
                widthMm = 135f, heightMm = 55f, depthMm = 30f, weightG = 850f,
                certifications = "EN 1154, EN 1155",
                price = 2295.0, currency = "SEK", isCustom = false, createdAt = now
            ),
            Product(
                id = "prod_assa_access_controller",
                name = "Passerkontroller ARX4",
                manufacturer = "ASSA Abloy",
                articleNumber = "ARX-C4",
                category = "ACCESS",
                description = "IP-ansluten passerkontroller för 4 dörrar, PoE, inbyggd Wiegand.",
                powerStandbyMa = 150f, powerAlarmMa = 200f, voltageV = 12f,
                widthMm = 185f, heightMm = 230f, depthMm = 55f, weightG = 900f,
                certifications = "EN 60839-11-1",
                price = 7500.0, currency = "SEK", isCustom = false, createdAt = now
            ),

            // ── CCTV ─────────────────────────────────────────────────────────
            Product(
                id = "prod_axis_dome_p32",
                name = "Dome-kamera AXIS P3245-V",
                manufacturer = "Axis",
                articleNumber = "01588-001",
                category = "CCTV",
                description = "Full HD dome-kamera, HDTV 1080p, IR-belysning 10m, IP42, PoE.",
                powerWatt = 6.5f, voltageV = 48f,
                widthMm = 135f, heightMm = 100f, depthMm = 135f, weightG = 490f,
                certifications = "IP42, IK08, ONVIF S/G/T",
                price = 3950.0, currency = "SEK", isCustom = false, createdAt = now
            ),
            Product(
                id = "prod_hikvision_bullet",
                name = "Bullet-kamera DS-2CD2T47",
                manufacturer = "Hikvision",
                articleNumber = "DS-2CD2T47G2-L",
                category = "CCTV",
                description = "4MP ColorVu bullet-kamera, 60m färg-IR, IP67, PoE.",
                powerWatt = 7.5f, voltageV = 48f,
                widthMm = 94f, heightMm = 94f, depthMm = 262f, weightG = 820f,
                certifications = "IP67, IK10, ONVIF S",
                price = 1595.0, currency = "SEK", isCustom = false, createdAt = now
            ),
            Product(
                id = "prod_dahua_ptz",
                name = "PTZ-kamera SD49425",
                manufacturer = "Dahua",
                articleNumber = "SD49425XB-HNR",
                category = "CCTV",
                description = "4MP PTZ med 25x optisk zoom, IR 100m, IP66, PoE+.",
                powerWatt = 25f, voltageV = 48f,
                widthMm = 163f, heightMm = 235f, depthMm = 163f, weightG = 1820f,
                certifications = "IP66, IK10, ONVIF S",
                price = 8950.0, currency = "SEK", isCustom = false, createdAt = now
            ),
            Product(
                id = "prod_hikvision_nvr",
                name = "NVR DS-7616NI-K2/16P",
                manufacturer = "Hikvision",
                articleNumber = "DS-7616NI-K2/16P",
                category = "CCTV",
                description = "16-kanals NVR med inbyggd 16-portars PoE-switch, stöd upp till 12MP.",
                powerWatt = 120f, voltageV = 230f,
                widthMm = 385f, heightMm = 55f, depthMm = 310f, weightG = 3200f,
                certifications = "CE, FCC, ONVIF",
                price = 6500.0, currency = "SEK", isCustom = false, createdAt = now
            ),

            // ── DOOR ─────────────────────────────────────────────────────────
            Product(
                id = "prod_abloy_maglok",
                name = "Elektromagnetiskt dörrbroms 600N",
                manufacturer = "Abloy",
                articleNumber = "EL550",
                category = "DOOR",
                description = "Elektromagnetisk hållarmagnet 600N för branddörrar. Fail-safe.",
                powerStandbyMa = 5f, powerAlarmMa = 500f, voltageV = 12f,
                widthMm = 145f, heightMm = 50f, depthMm = 30f, weightG = 1200f,
                certifications = "EN 1634-1, EN 1155",
                price = 3200.0, currency = "SEK", isCustom = false, createdAt = now
            ),
            Product(
                id = "prod_assa_el_strike",
                name = "Elvråk ASSA AR310",
                manufacturer = "ASSA Abloy",
                articleNumber = "AR310-12",
                category = "DOOR",
                description = "Reversibel elslutbleck för cylinderlås. Fail-secure, 12V DC.",
                powerStandbyMa = 0f, powerAlarmMa = 300f, voltageV = 12f,
                widthMm = 64f, heightMm = 25f, depthMm = 20f, weightG = 220f,
                certifications = "EN 12209",
                price = 1450.0, currency = "SEK", isCustom = false, createdAt = now
            ),

            // ── INTERCOM ─────────────────────────────────────────────────────
            Product(
                id = "prod_axis_doorstation",
                name = "IP-porttelefon AXIS A8105-E",
                manufacturer = "Axis",
                articleNumber = "01290-001",
                category = "INTERCOM",
                description = "Utomhus video-porttelefon med Full HD-kamera, PoE, IP65.",
                powerWatt = 6.0f, voltageV = 48f,
                widthMm = 78f, heightMm = 250f, depthMm = 40f, weightG = 700f,
                certifications = "IP65, IK08, ONVIF T",
                price = 5500.0, currency = "SEK", isCustom = false, createdAt = now
            ),
            Product(
                id = "prod_axis_indoor_unit",
                name = "Inomhusmonitor AXIS I8016-LVE",
                manufacturer = "Axis",
                articleNumber = "02603-001",
                category = "INTERCOM",
                description = "Video-dörrtelefon inomhusenhet med 7\" pekskärm, PoE.",
                powerWatt = 9.0f, voltageV = 48f,
                widthMm = 130f, heightMm = 185f, depthMm = 25f, weightG = 600f,
                certifications = "CE, FCC, ONVIF T",
                price = 4200.0, currency = "SEK", isCustom = false, createdAt = now
            )
        )
        products.forEach { productRepository.saveProduct(it) }
    }

    private suspend fun seedProtocolTemplates() {
        if (templateRepository.getTemplateCount() >= 4) return

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
            ),
            ProtocolTemplate(
                id = "functional_description_v1",
                name = "Anläggningsbeskrivning",
                systemCategory = "GENERAL",
                description = "Strukturerad beskrivning av hela säkerhetssystemet per SSF-standard.",
                fieldsJson = """[
                  {"id":"customer","label":"Kund","type":"text","required":true},
                  {"id":"property","label":"Fastighet","type":"text","required":true},
                  {"id":"address","label":"Adress","type":"text","required":true},
                  {"id":"installer","label":"Ansvarig installatör","type":"text","required":true},
                  {"id":"install_date","label":"Installationsdatum","type":"date","required":true},
                  {"id":"systems","label":"Systemtyper (Inbrottslarm/Brandlarm/Passage/CCTV/Porttelefon)","type":"text","required":true},
                  {"id":"alarm_central_make","label":"Larmcentral fabrikat","type":"text","required":false},
                  {"id":"alarm_central_model","label":"Larmcentral modell","type":"text","required":false},
                  {"id":"alarm_grade","label":"Larmklass (1-4)","type":"number","required":false},
                  {"id":"zones_count","label":"Antal zoner","type":"number","required":false},
                  {"id":"detectors_count","label":"Antal detektorer","type":"number","required":false},
                  {"id":"entry_delay","label":"Utlösningsfördröjning ingång (s)","type":"number","required":false},
                  {"id":"exit_delay","label":"Utlösningsfördröjning utgång (s)","type":"number","required":false},
                  {"id":"panic_alarm","label":"Överfallslarm","type":"checkbox","required":false},
                  {"id":"arc_connected","label":"Ansluten till larmcentral","type":"checkbox","required":false},
                  {"id":"arc_operator","label":"Larmcentralsoperatör","type":"text","required":false},
                  {"id":"nvr_make","label":"NVR/DVR fabrikat","type":"text","required":false},
                  {"id":"nvr_model","label":"NVR/DVR modell","type":"text","required":false},
                  {"id":"cameras_count","label":"Antal kameror","type":"number","required":false},
                  {"id":"recording_days","label":"Inspelningsdygn","type":"number","required":false},
                  {"id":"remote_access","label":"Fjärråtkomst","type":"checkbox","required":false},
                  {"id":"camera_type","label":"Kameratyp","type":"text","required":false},
                  {"id":"access_make","label":"Passagesystem fabrikat","type":"text","required":false},
                  {"id":"access_model","label":"Passagesystem modell","type":"text","required":false},
                  {"id":"access_doors","label":"Antal dörrar","type":"number","required":false},
                  {"id":"card_format","label":"Kortformat","type":"text","required":false},
                  {"id":"antipassback","label":"Antipassback","type":"checkbox","required":false},
                  {"id":"general_notes","label":"Generella anteckningar","type":"textarea","required":false},
                  {"id":"special_instructions","label":"Specialinstruktioner","type":"textarea","required":false},
                  {"id":"approval_date","label":"Godkännandedatum","type":"date","required":false},
                  {"id":"technician_sign","label":"Teknikerns underskrift","type":"text","required":false},
                  {"id":"customer_sign","label":"Kundens underskrift","type":"text","required":false}
                ]""",
                version = 1,
                isBuiltIn = true,
                createdAt = now
            )
        )
        templates.forEach { templateRepository.saveTemplate(it) }
    }
}
