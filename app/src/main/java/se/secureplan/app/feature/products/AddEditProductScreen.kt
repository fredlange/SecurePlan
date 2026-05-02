package se.secureplan.app.feature.products

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import se.secureplan.app.core.domain.model.Product
import java.util.UUID

private val CATEGORIES = listOf(
    "INTRUSION" to "Inbrottslarm",
    "FIRE"      to "Brandlarm",
    "ACCESS"    to "Passerkontroll",
    "CCTV"      to "CCTV",
    "INTERCOM"  to "Porttelefon",
    "DOOR"      to "Dörr",
    "OTHER"     to "Övrigt"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditProductScreen(
    productId: String?,        // null = new product
    onSaved: () -> Unit,
    onBack: () -> Unit,
    viewModel: ProductsViewModel = hiltViewModel()
) {
    val isEditMode = productId != null
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val existing = remember(productId, uiState.products) {
        productId?.let { id -> uiState.products.find { it.id == id } }
    }

    // Form state
    var name           by remember(existing) { mutableStateOf(existing?.name ?: "") }
    var manufacturer   by remember(existing) { mutableStateOf(existing?.manufacturer ?: "") }
    var articleNumber  by remember(existing) { mutableStateOf(existing?.articleNumber ?: "") }
    var category       by remember(existing) { mutableStateOf(existing?.category ?: "INTRUSION") }
    var description    by remember(existing) { mutableStateOf(existing?.description ?: "") }
    var standbyMa      by remember(existing) { mutableStateOf(existing?.powerStandbyMa?.toString() ?: "") }
    var alarmMa        by remember(existing) { mutableStateOf(existing?.powerAlarmMa?.toString() ?: "") }
    var voltageV       by remember(existing) { mutableStateOf(existing?.voltageV?.toString() ?: "12") }
    var powerWatt      by remember(existing) { mutableStateOf(existing?.powerWatt?.toString() ?: "") }
    var widthMm        by remember(existing) { mutableStateOf(existing?.widthMm?.toString() ?: "") }
    var heightMm       by remember(existing) { mutableStateOf(existing?.heightMm?.toString() ?: "") }
    var depthMm        by remember(existing) { mutableStateOf(existing?.depthMm?.toString() ?: "") }
    var weightG        by remember(existing) { mutableStateOf(existing?.weightG?.toString() ?: "") }
    var certifications by remember(existing) { mutableStateOf(existing?.certifications ?: "") }
    var price          by remember(existing) { mutableStateOf(existing?.price?.toString() ?: "") }

    var categoryExpanded by remember { mutableStateOf(false) }
    var nameError        by remember { mutableStateOf(false) }
    var mfgError         by remember { mutableStateOf(false) }

    fun onSave() {
        nameError = name.isBlank()
        mfgError  = manufacturer.isBlank()
        if (nameError || mfgError) return

        val product = Product(
            id              = existing?.id ?: UUID.randomUUID().toString(),
            name            = name.trim(),
            manufacturer    = manufacturer.trim(),
            articleNumber   = articleNumber.trim(),
            category        = category,
            description     = description.trim(),
            powerStandbyMa  = standbyMa.toFloatOrNull() ?: 0f,
            powerAlarmMa    = alarmMa.toFloatOrNull() ?: 0f,
            voltageV        = voltageV.toFloatOrNull() ?: 12f,
            powerWatt       = powerWatt.toFloatOrNull() ?: 0f,
            widthMm         = widthMm.toFloatOrNull() ?: 0f,
            heightMm        = heightMm.toFloatOrNull() ?: 0f,
            depthMm         = depthMm.toFloatOrNull() ?: 0f,
            weightG         = weightG.toFloatOrNull() ?: 0f,
            certifications  = certifications.trim(),
            price           = price.toDoubleOrNull() ?: 0.0,
            currency        = existing?.currency ?: "SEK",
            isCustom        = true,
            createdAt       = existing?.createdAt ?: System.currentTimeMillis()
        )
        viewModel.saveProduct(product)
        onSaved()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (isEditMode) "Redigera produkt" else "Ny produkt",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Tillbaka",
                            tint = MaterialTheme.colorScheme.onPrimary)
                    }
                },
                actions = {
                    TextButton(onClick = ::onSave) {
                        Text("Spara",
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontWeight = FontWeight.Bold)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // ── Grundinformation ─────────────────────────────────────────────
            FormSectionHeader("Grundinformation")

            OutlinedTextField(
                value = name, onValueChange = { name = it; nameError = false },
                label = { Text("Produktnamn *") },
                modifier = Modifier.fillMaxWidth(),
                isError = nameError,
                supportingText = if (nameError) {{ Text("Produktnamn krävs") }} else null,
                singleLine = true
            )
            OutlinedTextField(
                value = manufacturer, onValueChange = { manufacturer = it; mfgError = false },
                label = { Text("Tillverkare *") },
                modifier = Modifier.fillMaxWidth(),
                isError = mfgError,
                supportingText = if (mfgError) {{ Text("Tillverkare krävs") }} else null,
                singleLine = true
            )
            OutlinedTextField(
                value = articleNumber, onValueChange = { articleNumber = it },
                label = { Text("Artikelnummer") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Category dropdown
            ExposedDropdownMenuBox(
                expanded = categoryExpanded,
                onExpandedChange = { categoryExpanded = it }
            ) {
                OutlinedTextField(
                    value = CATEGORIES.find { it.first == category }?.second ?: category,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Kategori *") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = categoryExpanded) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = categoryExpanded,
                    onDismissRequest = { categoryExpanded = false }
                ) {
                    CATEGORIES.forEach { (key, label) ->
                        DropdownMenuItem(
                            text = { Text(label) },
                            onClick = { category = key; categoryExpanded = false }
                        )
                    }
                }
            }

            OutlinedTextField(
                value = description, onValueChange = { description = it },
                label = { Text("Beskrivning") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 2, maxLines = 4
            )

            // ── Elektriska specifikationer ────────────────────────────────────
            FormSectionHeader("Elektriska specifikationer")

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = standbyMa,
                    onValueChange = { standbyMa = it.filter { c -> c.isDigit() || c == '.' } },
                    label = { Text("Vilström (mA)") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    singleLine = true
                )
                OutlinedTextField(
                    value = alarmMa,
                    onValueChange = { alarmMa = it.filter { c -> c.isDigit() || c == '.' } },
                    label = { Text("Larmström (mA)") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    singleLine = true
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = voltageV,
                    onValueChange = { voltageV = it.filter { c -> c.isDigit() || c == '.' } },
                    label = { Text("Spänning (V)") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    singleLine = true
                )
                OutlinedTextField(
                    value = powerWatt,
                    onValueChange = { powerWatt = it.filter { c -> c.isDigit() || c == '.' } },
                    label = { Text("Effekt (W) / PoE") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    singleLine = true
                )
            }

            // ── Fysiska mått ─────────────────────────────────────────────────
            FormSectionHeader("Fysiska mått (valfritt)")

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = widthMm,
                    onValueChange = { widthMm = it.filter { c -> c.isDigit() || c == '.' } },
                    label = { Text("Bredd (mm)") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    singleLine = true
                )
                OutlinedTextField(
                    value = heightMm,
                    onValueChange = { heightMm = it.filter { c -> c.isDigit() || c == '.' } },
                    label = { Text("Höjd (mm)") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    singleLine = true
                )
                OutlinedTextField(
                    value = depthMm,
                    onValueChange = { depthMm = it.filter { c -> c.isDigit() || c == '.' } },
                    label = { Text("Djup (mm)") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    singleLine = true
                )
            }
            OutlinedTextField(
                value = weightG,
                onValueChange = { weightG = it.filter { c -> c.isDigit() || c == '.' } },
                label = { Text("Vikt (g)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                singleLine = true
            )

            // ── Övrigt ───────────────────────────────────────────────────────
            FormSectionHeader("Övrigt")

            OutlinedTextField(
                value = certifications, onValueChange = { certifications = it },
                label = { Text("Certifieringar (kommaseparerat)") },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("t.ex. SSF 1014, EN 50131-2") },
                singleLine = true
            )
            OutlinedTextField(
                value = price,
                onValueChange = { price = it.filter { c -> c.isDigit() || c == '.' } },
                label = { Text("Pris (SEK)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                singleLine = true
            )

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = ::onSave,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Save, contentDescription = null,
                    modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(8.dp))
                Text(if (isEditMode) "Uppdatera produkt" else "Spara produkt")
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun FormSectionHeader(title: String) {
    Text(
        title,
        style = MaterialTheme.typography.titleSmall,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(top = 4.dp)
    )
    HorizontalDivider()
}
