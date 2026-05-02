package se.secureplan.app.feature.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import se.secureplan.app.core.domain.model.Drawing
import se.secureplan.app.core.domain.model.Project
import se.secureplan.app.core.domain.repository.DrawingRepository
import se.secureplan.app.core.domain.repository.ProjectRepository
import java.util.UUID
import javax.inject.Inject

// ─── ViewModel ───────────────────────────────────────────────────────────────

data class DashboardUiState(
    val project: Project? = null,
    val drawings: List<Drawing> = emptyList(),
    val isLoading: Boolean = true,
    val showAddDrawingDialog: Boolean = false
)

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val projectRepository: ProjectRepository,
    private val drawingRepository: DrawingRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    private var currentProjectId: String? = null

    fun loadProject(projectId: String) {
        if (currentProjectId == projectId) return
        currentProjectId = projectId
        viewModelScope.launch {
            combine(
                projectRepository.getProjectById(projectId),
                drawingRepository.getDrawingsForProject(projectId)
            ) { project, drawings -> Pair(project, drawings) }
                .collect { (project, drawings) ->
                    _uiState.update { it.copy(project = project, drawings = drawings, isLoading = false) }
                }
        }
    }

    fun showAddDrawingDialog() = _uiState.update { it.copy(showAddDrawingDialog = true) }
    fun hideAddDrawingDialog() = _uiState.update { it.copy(showAddDrawingDialog = false) }

    fun createDrawing(name: String, floor: Int) {
        val projectId = currentProjectId ?: return
        viewModelScope.launch {
            val now = System.currentTimeMillis()
            val drawing = Drawing(
                id = UUID.randomUUID().toString(),
                projectId = projectId,
                name = name,
                floor = floor,
                backgroundUri = null,
                backgroundPageIndex = 0,
                scaleMetersPerUnit = 1.0f,
                createdAt = now,
                updatedAt = now,
                width = 1000f,
                height = 1000f
            )
            drawingRepository.saveDrawing(drawing)
            hideAddDrawingDialog()
        }
    }

    fun deleteDrawing(id: String) {
        viewModelScope.launch { drawingRepository.deleteDrawing(id) }
    }
}

// ─── Screen ──────────────────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    projectId: String,
    onDrawingClick: (String) -> Unit,
    onBack: () -> Unit,
    onComponentsClick: () -> Unit = {},
    onCalculationsClick: () -> Unit = {},
    onPhotosClick: () -> Unit = {},
    onProductsClick: () -> Unit = {},
    onProtocolsClick: () -> Unit = {},
    onExportClick: () -> Unit = {},
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(projectId) { viewModel.loadProject(projectId) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(uiState.project?.name ?: "Project",
                            fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)
                        uiState.project?.clientName?.let {
                            Text(it, style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f))
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onPrimary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                actions = {
                    IconButton(onClick = onExportClick) {
                        Icon(Icons.Default.Share, contentDescription = "Export",
                            tint = MaterialTheme.colorScheme.onPrimary)
                    }
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { viewModel.showAddDrawingDialog() },
                icon = { Icon(Icons.Default.Add, contentDescription = null) },
                text = { Text("Add Drawing") }
            )
        }
    ) { padding ->
        if (uiState.isLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Project info card
                item {
                    uiState.project?.let { ProjectInfoSection(it) }
                }

                // Stats row
                item {
                    DashboardStatsRow(drawingCount = uiState.drawings.size)
                }

                // Drawings section
                item {
                    Text("Floor Plans & Drawings",
                        style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                }

                if (uiState.drawings.isEmpty()) {
                    item {
                        Card(modifier = Modifier.fillMaxWidth()) {
                            Box(
                                modifier = Modifier.fillMaxWidth().padding(32.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Icon(Icons.Default.Map, contentDescription = null,
                                        modifier = Modifier.size(48.dp),
                                        tint = MaterialTheme.colorScheme.outline)
                                    Spacer(Modifier.height(8.dp))
                                    Text("No drawings yet", style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.outline)
                                }
                            }
                        }
                    }
                } else {
                    items(uiState.drawings, key = { it.id }) { drawing ->
                        DrawingCard(
                            drawing = drawing,
                            onClick = { onDrawingClick(drawing.id) },
                            onDelete = { viewModel.deleteDrawing(drawing.id) }
                        )
                    }
                }

                // Quick action cards
                item {
                    Text("Quick Actions", style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold)
                }
                item {
                    QuickActionsRow(
                        onPhotosClick       = onPhotosClick,
                        onCalculationsClick = onCalculationsClick,
                        onComponentsClick   = onComponentsClick,
                        onProductsClick     = onProductsClick,
                        onProtocolsClick    = onProtocolsClick,
                        onExportClick       = onExportClick
                    )
                }
                item { Spacer(Modifier.height(80.dp)) }
            }
        }
    }

    if (uiState.showAddDrawingDialog) {
        AddDrawingDialog(
            onDismiss = viewModel::hideAddDrawingDialog,
            onCreate = viewModel::createDrawing
        )
    }
}

@Composable
private fun ProjectInfoSection(project: Project) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.LocationOn, contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp))
                Spacer(Modifier.width(4.dp))
                Text(project.address.ifBlank { "No address" },
                    style = MaterialTheme.typography.bodyMedium)
            }
            if (project.installerName.isNotBlank()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Person, contentDescription = null,
                        tint = MaterialTheme.colorScheme.secondary, modifier = Modifier.size(16.dp))
                    Spacer(Modifier.width(4.dp))
                    Text("${project.installerName} · ${project.installerCompany}",
                        style = MaterialTheme.typography.bodyMedium)
                }
            }
            if (project.description.isNotBlank()) {
                Text(project.description, style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}

@Composable
private fun DashboardStatsRow(drawingCount: Int) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        StatCard("Drawings", drawingCount.toString(), Icons.Default.Map, Modifier.weight(1f))
        StatCard("Photos", "–", Icons.Default.PhotoCamera, Modifier.weight(1f))
        StatCard("Protocols", "–", Icons.Default.Assignment, Modifier.weight(1f))
    }
}

@Composable
private fun StatCard(label: String, value: String, icon: androidx.compose.ui.graphics.vector.ImageVector, modifier: Modifier) {
    Card(modifier = modifier) {
        Column(modifier = Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            Text(value, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Text(label, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.outline)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DrawingCard(drawing: Drawing, onClick: () -> Unit, onDelete: () -> Unit) {
    var showDelete by remember { mutableStateOf(false) }
    Card(onClick = onClick, modifier = Modifier.fillMaxWidth()) {
        ListItem(
            headlineContent = { Text(drawing.name, fontWeight = FontWeight.Medium) },
            supportingContent = { Text("Floor ${drawing.floor}") },
            leadingContent = {
                Surface(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = MaterialTheme.shapes.small
                ) {
                    Box(Modifier.size(40.dp), contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.Map, contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary)
                    }
                }
            },
            trailingContent = {
                IconButton(onClick = { showDelete = true }) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete",
                        tint = MaterialTheme.colorScheme.outline)
                }
            }
        )
    }
    if (showDelete) {
        AlertDialog(
            onDismissRequest = { showDelete = false },
            title = { Text("Delete drawing?") },
            text = { Text("All symbols, cables and zones on \"${drawing.name}\" will be deleted.") },
            confirmButton = {
                TextButton(onClick = { onDelete(); showDelete = false }) {
                    Text("Delete", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = { TextButton(onClick = { showDelete = false }) { Text("Cancel") } }
        )
    }
}

@Composable
private fun QuickActionsRow(
    onPhotosClick: () -> Unit = {},
    onCalculationsClick: () -> Unit = {},
    onComponentsClick: () -> Unit = {},
    onProductsClick: () -> Unit = {},
    onProtocolsClick: () -> Unit = {},
    onExportClick: () -> Unit = {}
) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        item { QuickActionChip(Icons.Default.PhotoCamera,  "Foton",       onPhotosClick) }
        item { QuickActionChip(Icons.Default.Calculate,    "Beräkningar", onCalculationsClick) }
        item { QuickActionChip(Icons.Default.List,         "Komponenter", onComponentsClick) }
        item { QuickActionChip(Icons.Default.Inventory2,   "Produkter",   onProductsClick) }
        item { QuickActionChip(Icons.Default.Assignment,   "Protokoll",   onProtocolsClick) }
        item { QuickActionChip(Icons.Default.PictureAsPdf, "Exportera",   onExportClick) }
    }
}

@Composable
private fun QuickActionChip(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    onClick: () -> Unit = {}
) {
    ElevatedCard(onClick = onClick) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.dp))
            Text(label, style = MaterialTheme.typography.labelLarge)
        }
    }
}

@Composable
private fun AddDrawingDialog(onDismiss: () -> Unit, onCreate: (String, Int) -> Unit) {
    var name by remember { mutableStateOf("") }
    var floor by remember { mutableStateOf("0") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("New Drawing") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = name, onValueChange = { name = it },
                    label = { Text("Drawing name *") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
                OutlinedTextField(
                    value = floor, onValueChange = { floor = it.filter { c -> c.isDigit() || c == '-' } },
                    label = { Text("Floor number") }, modifier = Modifier.fillMaxWidth(), singleLine = true
                )
            }
        },
        confirmButton = {
            Button(onClick = { if (name.isNotBlank()) onCreate(name, floor.toIntOrNull() ?: 0) },
                enabled = name.isNotBlank()) { Text("Create") }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancel") } }
    )
}
