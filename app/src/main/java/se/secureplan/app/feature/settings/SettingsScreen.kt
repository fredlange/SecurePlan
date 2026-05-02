package se.secureplan.app.feature.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import se.secureplan.app.core.settings.SettingsManager
import javax.inject.Inject

data class SettingsUiState(
    val companyName: String = "",
    val defaultTechnician: String = "",
    val currency: String = "SEK",
    val isDarkMode: Boolean = false
)

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsManager: SettingsManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        SettingsUiState(
            companyName = settingsManager.companyName,
            defaultTechnician = settingsManager.defaultTechnician,
            currency = settingsManager.currency,
            isDarkMode = settingsManager.isDarkMode
        )
    )
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    fun updateCompanyName(name: String) {
        settingsManager.companyName = name
        _uiState.update { it.copy(companyName = name) }
    }

    fun updateDefaultTechnician(name: String) {
        settingsManager.defaultTechnician = name
        _uiState.update { it.copy(defaultTechnician = name) }
    }

    fun updateCurrency(currency: String) {
        settingsManager.currency = currency
        _uiState.update { it.copy(currency = currency) }
    }

    fun toggleDarkMode(enabled: Boolean) {
        settingsManager.isDarkMode = enabled
        _uiState.update { it.copy(isDarkMode = enabled) }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()
    var showCurrencyMenu by remember { mutableStateOf(false) }
    val currencies = listOf("SEK", "EUR", "NOK", "DKK")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Inställningar") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Tillbaka")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(scrollState)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                "Företag",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary
            )
            OutlinedTextField(
                value = uiState.companyName,
                onValueChange = { viewModel.updateCompanyName(it) },
                label = { Text("Företagsnamn") },
                leadingIcon = { Icon(Icons.Default.Business, contentDescription = null) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Text(
                "Standard",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary
            )
            OutlinedTextField(
                value = uiState.defaultTechnician,
                onValueChange = { viewModel.updateDefaultTechnician(it) },
                label = { Text("Teknikerns standardnamn") },
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            ExposedDropdownMenuBox(
                expanded = showCurrencyMenu,
                onExpandedChange = { showCurrencyMenu = it }
            ) {
                OutlinedTextField(
                    value = uiState.currency,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Valuta") },
                    leadingIcon = { Icon(Icons.Default.AttachMoney, contentDescription = null) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = showCurrencyMenu) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = showCurrencyMenu,
                    onDismissRequest = { showCurrencyMenu = false }
                ) {
                    currencies.forEach { currency ->
                        DropdownMenuItem(
                            text = { Text(currency) },
                            onClick = {
                                viewModel.updateCurrency(currency)
                                showCurrencyMenu = false
                            }
                        )
                    }
                }
            }

            HorizontalDivider()

            Text(
                "Utseende",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary
            )
            ListItem(
                headlineContent = { Text("Mörkt läge") },
                supportingContent = { Text("Aktivera mörkt tema") },
                leadingContent = { Icon(Icons.Default.DarkMode, contentDescription = null) },
                trailingContent = {
                    Switch(
                        checked = uiState.isDarkMode,
                        onCheckedChange = { viewModel.toggleDarkMode(it) }
                    )
                }
            )

            HorizontalDivider()

            Text(
                "Om appen",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary
            )
            ListItem(
                headlineContent = { Text("SecurePlan") },
                supportingContent = { Text("Version 1.0.0 · Projektering av säkerhetssystem") },
                leadingContent = { Icon(Icons.Default.Info, contentDescription = null) }
            )
            Spacer(Modifier.height(32.dp))
        }
    }
}
