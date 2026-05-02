package se.secureplan.app.feature.protocols

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import se.secureplan.app.core.domain.model.ProtocolInstance
import se.secureplan.app.core.domain.model.ProtocolTemplate
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProtocolsScreen(
    projectId: String,
    onBack: () -> Unit,
    onOpenForm: (templateId: String?, instanceId: String?) -> Unit,
    viewModel: ProtocolsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Protokoll", "Funktionsbeskrivning")

    LaunchedEffect(projectId) { viewModel.loadForProject(projectId) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Dokument & Protokoll") },
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
        },
        floatingActionButton = {
            if (selectedTab == 0) {
                FloatingActionButton(onClick = { viewModel.showTemplatePicker() }) {
                    Icon(Icons.Default.Add, contentDescription = "Nytt protokoll")
                }
            }
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            TabRow(selectedTabIndex = selectedTab) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(title) }
                    )
                }
            }

            when (selectedTab) {
                0 -> ProtocolListTab(
                    instances = uiState.instances,
                    templates = uiState.templates,
                    onInstanceClick = { instance -> onOpenForm(null, instance.id) },
                    onDeleteInstance = { viewModel.deleteInstance(it) }
                )
                1 -> FunctionalDescriptionScreen(
                    projectId = projectId,
                    onOpenForm = { templateId, instanceId -> onOpenForm(templateId, instanceId) }
                )
            }
        }
    }

    if (uiState.showTemplatePicker) {
        TemplatePickerSheet(
            templates = uiState.templates,
            onTemplateSelected = { template ->
                viewModel.hideTemplatePicker()
                onOpenForm(template.id, null)
            },
            onDismiss = { viewModel.hideTemplatePicker() }
        )
    }
}

@Composable
private fun ProtocolListTab(
    instances: List<ProtocolInstance>,
    templates: List<ProtocolTemplate>,
    onInstanceClick: (ProtocolInstance) -> Unit,
    onDeleteInstance: (String) -> Unit
) {
    if (instances.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    Icons.Default.Assignment,
                    contentDescription = null,
                    modifier = Modifier.size(64.dp),
                    tint = MaterialTheme.colorScheme.outline
                )
                Text(
                    "Inga protokoll ännu",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.outline
                )
                Text(
                    "Tryck + för att skapa ett nytt protokoll",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.outline
                )
            }
        }
    } else {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(instances, key = { it.id }) { instance ->
                val templateName = templates.find { it.id == instance.templateId }?.name ?: instance.templateId
                ProtocolInstanceCard(
                    instance = instance,
                    templateName = templateName,
                    onClick = { onInstanceClick(instance) },
                    onDelete = { onDeleteInstance(instance.id) }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProtocolInstanceCard(
    instance: ProtocolInstance,
    templateName: String,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    val dateFormat = remember { SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()) }
    var showDelete by remember { mutableStateOf(false) }

    Card(onClick = onClick, modifier = Modifier.fillMaxWidth()) {
        ListItem(
            headlineContent = { Text(templateName, fontWeight = FontWeight.Medium) },
            supportingContent = {
                Column {
                    Text(dateFormat.format(Date(instance.updatedAt)))
                    instance.signedBy?.let {
                        Text("Teknikern: $it", style = MaterialTheme.typography.bodySmall)
                    }
                }
            },
            trailingContent = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    StatusBadge(instance.status)
                    IconButton(onClick = { showDelete = true }) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "Radera",
                            tint = MaterialTheme.colorScheme.outline
                        )
                    }
                }
            },
            leadingContent = {
                Surface(
                    color = if (instance.status == "COMPLETED")
                        MaterialTheme.colorScheme.primaryContainer
                    else MaterialTheme.colorScheme.secondaryContainer,
                    shape = MaterialTheme.shapes.small
                ) {
                    Box(Modifier.size(40.dp), contentAlignment = Alignment.Center) {
                        Icon(
                            Icons.Default.Assignment,
                            contentDescription = null,
                            tint = if (instance.status == "COMPLETED")
                                MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.secondary
                        )
                    }
                }
            }
        )
    }

    if (showDelete) {
        AlertDialog(
            onDismissRequest = { showDelete = false },
            title = { Text("Radera protokoll?") },
            text = { Text("Protokollet \"$templateName\" raderas permanent.") },
            confirmButton = {
                TextButton(onClick = { onDelete(); showDelete = false }) {
                    Text("Radera", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDelete = false }) { Text("Avbryt") }
            }
        )
    }
}

@Composable
private fun StatusBadge(status: String) {
    val (label, color) = when (status) {
        "COMPLETED" -> "Signerad" to MaterialTheme.colorScheme.primary
        else -> "Utkast" to MaterialTheme.colorScheme.tertiary
    }
    SuggestionChip(
        onClick = {},
        label = { Text(label, style = MaterialTheme.typography.labelSmall) },
        colors = SuggestionChipDefaults.suggestionChipColors(
            containerColor = color.copy(alpha = 0.15f),
            labelColor = color
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TemplatePickerSheet(
    templates: List<ProtocolTemplate>,
    onTemplateSelected: (ProtocolTemplate) -> Unit,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState
    ) {
        Column(modifier = Modifier.padding(bottom = 32.dp)) {
            Text(
                "Välj protokollmall",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
            )
            HorizontalDivider()
            val builtIn = templates.filter { it.isBuiltIn }
            val custom = templates.filter { !it.isBuiltIn }
            if (builtIn.isNotEmpty()) {
                Text(
                    "Inbyggda mallar",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.outline,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
                builtIn.forEach { template ->
                    ListItem(
                        headlineContent = { Text(template.name) },
                        supportingContent = { Text(template.description, maxLines = 1) },
                        modifier = Modifier.clickable { onTemplateSelected(template) }
                    )
                }
            }
            if (custom.isNotEmpty()) {
                HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
                Text(
                    "Egna mallar",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.outline,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
                custom.forEach { template ->
                    ListItem(
                        headlineContent = { Text(template.name) },
                        supportingContent = { Text(template.description, maxLines = 1) },
                        modifier = Modifier.clickable { onTemplateSelected(template) }
                    )
                }
            }
        }
    }
}
