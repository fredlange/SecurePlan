package se.secureplan.app.feature.calculations

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

private val tabTitles = listOf("Inbrottslarm (SSF 130)", "CCTV (SSF 1060)")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculationsScreen(
    projectId: String,
    onBack: () -> Unit,
    viewModel: CalculationsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var selectedTab by remember { mutableIntStateOf(0) }

    LaunchedEffect(projectId) { viewModel.loadProject(projectId) }

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = { Text("Beräkningar", fontWeight = FontWeight.Bold) },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Tillbaka",
                                tint = MaterialTheme.colorScheme.onPrimary)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
                TabRow(
                    selectedTabIndex = selectedTab,
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ) {
                    tabTitles.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTab == index,
                            onClick = { selectedTab = index },
                            text = { Text(title, style = MaterialTheme.typography.labelMedium) }
                        )
                    }
                }
            }
        }
    ) { padding ->
        if (uiState.isLoading) {
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
            return@Scaffold
        }

        when (selectedTab) {
            0 -> Ssf130Tab(
                uiState       = uiState,
                modifier      = Modifier.padding(padding),
                onStandbyChange = viewModel::updateComponentStandby,
                onAlarmChange   = viewModel::updateComponentAlarm,
                onCalculate     = viewModel::calculateSsf130
            )
            1 -> Ssf1060Tab(
                uiState       = uiState,
                modifier      = Modifier.padding(padding),
                onWattChange  = viewModel::updateCameraWatt,
                onCalculate   = viewModel::calculateSsf1060
            )
        }
    }
}

// ─── SSF 130 Tab ─────────────────────────────────────────────────────────────

@Composable
private fun Ssf130Tab(
    uiState: CalculationsUiState,
    modifier: Modifier,
    onStandbyChange: (Int, String) -> Unit,
    onAlarmChange: (Int, String) -> Unit,
    onCalculate: () -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Intro
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
            ) {
                Row(modifier = Modifier.padding(12.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon(Icons.Default.Info, contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSecondaryContainer,
                        modifier = Modifier.size(18.dp))
                    Text(
                        "Beräkning av strömförsörjning enl. SSF 130 Klass 2. " +
                            "Värden är förifyllda från länkade produkter — justera vid behov.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }
        }

        if (uiState.componentLoads.isEmpty()) {
            item {
                Box(Modifier.fillMaxWidth().padding(vertical = 32.dp), contentAlignment = Alignment.Center) {
                    Text("Inga inbrottslarmskomponenter placerade",
                        color = MaterialTheme.colorScheme.outline)
                }
            }
        } else {
            // Header row
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("Komponent", style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.weight(1.8f),
                        color = MaterialTheme.colorScheme.outline)
                    Text("Ant.", style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.width(32.dp),
                        color = MaterialTheme.colorScheme.outline)
                    Text("Vilo\n(mA)", style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.weight(1f),
                        color = MaterialTheme.colorScheme.outline)
                    Text("Larm\n(mA)", style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.weight(1f),
                        color = MaterialTheme.colorScheme.outline)
                }
                HorizontalDivider()
            }

            // Component rows
            itemsIndexed(uiState.componentLoads) { index, load ->
                ComponentLoadRow(
                    load           = load,
                    onStandbyChange = { onStandbyChange(index, it) },
                    onAlarmChange   = { onAlarmChange(index, it) }
                )
            }
        }

        // Calculate button
        item {
            Button(
                onClick = onCalculate,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                enabled = uiState.componentLoads.isNotEmpty()
            ) {
                Icon(Icons.Default.Calculate, contentDescription = null,
                    modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(8.dp))
                Text("Beräkna SSF 130")
            }
        }

        // Results
        uiState.ssf130Result?.let { result ->
            item { Ssf130ResultCard(result = result) }
        }

        item { Spacer(Modifier.height(16.dp)) }
    }
}

@Composable
private fun ComponentLoadRow(
    load: EditableComponentLoad,
    onStandbyChange: (String) -> Unit,
    onAlarmChange: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            load.name,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.weight(1.8f),
            maxLines = 2
        )
        Text(
            "${load.count}",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.width(32.dp),
            fontWeight = FontWeight.Bold
        )
        OutlinedTextField(
            value = load.standbyMaInput,
            onValueChange = { onStandbyChange(it.filter { c -> c.isDigit() || c == '.' }) },
            modifier = Modifier.weight(1f),
            textStyle = MaterialTheme.typography.bodySmall,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            singleLine = true
        )
        OutlinedTextField(
            value = load.alarmMaInput,
            onValueChange = { onAlarmChange(it.filter { c -> c.isDigit() || c == '.' }) },
            modifier = Modifier.weight(1f),
            textStyle = MaterialTheme.typography.bodySmall,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            singleLine = true
        )
    }
}

@Composable
private fun Ssf130ResultCard(result: Ssf130Calculator.Ssf130Result) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                "Beräkningsresultat — SSF 130 Klass 2",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            HorizontalDivider(color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.2f))

            ResultRow("Total vilström",  "%.1f mA".format(result.totalStandbyMa))
            ResultRow("Total larmström", "%.1f mA".format(result.totalAlarmMa))

            HorizontalDivider(color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.2f))

            ResultRow("Batteri Alt 1 (60h + 30 min)",
                "%.2f Ah".format(result.batteryCapacityAh_Alt1))
            ResultRow("Batteri Alt 2 (12h + 3 min)",
                "%.2f Ah".format(result.batteryCapacityAh_Alt2))

            HorizontalDivider(color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.2f))

            // Recommended battery — highlighted
            Surface(
                color = MaterialTheme.colorScheme.primary,
                shape = MaterialTheme.shapes.small,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Rekommenderat batteri",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Text(
                        "%.1f Ah".format(result.recommendedBatteryAh),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }

            ResultRow(
                "Nätdelsström (min)",
                "%.2f A vid %.1f V".format(result.powerSupplyCurrentA, result.powerSupplyVoltage)
            )
        }
    }
}

// ─── SSF 1060 Tab ────────────────────────────────────────────────────────────

@Composable
private fun Ssf1060Tab(
    uiState: CalculationsUiState,
    modifier: Modifier,
    onWattChange: (Int, String) -> Unit,
    onCalculate: () -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
            ) {
                Row(modifier = Modifier.padding(12.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon(Icons.Default.Info, contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSecondaryContainer,
                        modifier = Modifier.size(18.dp))
                    Text(
                        "Beräkning av strömförsörjning och UPS-kapacitet enl. SSF 1060. " +
                            "Ange effekt i Watt per kamerakropp.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }
        }

        if (uiState.cameraLoads.isEmpty()) {
            item {
                Box(Modifier.fillMaxWidth().padding(vertical = 32.dp), contentAlignment = Alignment.Center) {
                    Text("Inga kameror placerade", color = MaterialTheme.colorScheme.outline)
                }
            }
        } else {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("Kamera / Enhet", style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.weight(2f), color = MaterialTheme.colorScheme.outline)
                    Text("Ant.", style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.width(32.dp), color = MaterialTheme.colorScheme.outline)
                    Text("Watt\n(styck)", style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.weight(1f), color = MaterialTheme.colorScheme.outline)
                    Text("PoE", style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.width(32.dp), color = MaterialTheme.colorScheme.outline)
                }
                HorizontalDivider()
            }

            itemsIndexed(uiState.cameraLoads) { index, load ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(load.name, style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.weight(2f), maxLines = 2)
                    Text("${load.count}", style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.width(32.dp), fontWeight = FontWeight.Bold)
                    OutlinedTextField(
                        value = load.powerWattInput,
                        onValueChange = { onWattChange(index, it.filter { c -> c.isDigit() || c == '.' }) },
                        modifier = Modifier.weight(1f),
                        textStyle = MaterialTheme.typography.bodySmall,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        singleLine = true
                    )
                    Icon(
                        if (load.isPoE) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                        contentDescription = if (load.isPoE) "PoE" else "Ej PoE",
                        tint = if (load.isPoE) MaterialTheme.colorScheme.primary
                               else MaterialTheme.colorScheme.outline,
                        modifier = Modifier.width(32.dp).size(20.dp)
                    )
                }
            }
        }

        item {
            Button(
                onClick = onCalculate,
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                enabled = uiState.cameraLoads.isNotEmpty()
            ) {
                Icon(Icons.Default.Calculate, contentDescription = null,
                    modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(8.dp))
                Text("Beräkna SSF 1060")
            }
        }

        uiState.ssf1060Result?.let { result ->
            item { Ssf1060ResultCard(result = result) }
        }

        item { Spacer(Modifier.height(16.dp)) }
    }
}

@Composable
private fun Ssf1060ResultCard(result: Ssf1060Calculator.Ssf1060Result) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                "Beräkningsresultat — SSF 1060",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            HorizontalDivider(color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.2f))

            ResultRow("Total kameraeffekt",    "%.1f W".format(result.totalCameraWatt))
            if (result.nvrLoad != null)
                ResultRow("NVR-effekt (${result.nvrLoad.name})", "%.1f W".format(result.totalNvrWatt))
            ResultRow("Total systemeffekt",    "%.1f W".format(result.totalSystemWatt))
            ResultRow("PoE-budget (kameror)",  "%.1f W".format(result.poeBudgetWatt))

            HorizontalDivider(color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.2f))

            Surface(
                color = MaterialTheme.colorScheme.primary,
                shape = MaterialTheme.shapes.small,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("UPS-kapacitet (4h backup, 12V)",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary)
                    Text("%.1f Ah".format(result.upsBatteryAh),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.onPrimary)
                }
            }
        }
    }
}

@Composable
private fun ResultRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f),
            modifier = Modifier.weight(1f))
        Text(value, style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onPrimaryContainer)
    }
}
