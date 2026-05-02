package se.secureplan.app.feature.files

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import se.secureplan.app.core.domain.model.ProjectFile
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ProjectFileScreen(
    projectId: String,
    onBack: () -> Unit,
    viewModel: ProjectFileViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(projectId) { viewModel.loadFiles(projectId) }

    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearError()
        }
    }

    val filePicker = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let { viewModel.addFile(it) }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Projektfiler", fontWeight = FontWeight.Bold) },
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
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { filePicker.launch("*/*") },
                icon = { Icon(Icons.Default.Add, contentDescription = null) },
                text = { Text("Lägg till fil") }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        if (uiState.isLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (uiState.files.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Default.FolderOpen,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = MaterialTheme.colorScheme.outline
                    )
                    Spacer(Modifier.height(12.dp))
                    Text("Inga filer ännu",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.outline)
                    Spacer(Modifier.height(4.dp))
                    Text("Tryck på + för att lägga till en fil",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.outline)
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentPadding = PaddingValues(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                items(uiState.files, key = { it.id }) { file ->
                    ProjectFileItem(
                        file = file,
                        onTap = {
                            val fileUri = viewModel.getFileProviderUri(file)
                            if (fileUri == null) {
                                viewModel.clearError()
                                // trigger error via state
                                viewModel.showFileNotFoundError()
                            } else {
                                val intent = Intent(Intent.ACTION_VIEW).apply {
                                    setDataAndType(fileUri, file.mimeType)
                                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_ACTIVITY_NEW_TASK)
                                }
                                context.startActivity(Intent.createChooser(intent, "Öppna med"))
                            }
                        },
                        onLongPress = { viewModel.requestRename(file) },
                        onDelete = { viewModel.requestDelete(file) }
                    )
                }
                item { Spacer(Modifier.height(80.dp)) }
            }
        }
    }

    // Delete confirmation dialog
    if (uiState.showDeleteDialog) {
        AlertDialog(
            onDismissRequest = viewModel::cancelDelete,
            title = { Text("Ta bort fil?") },
            text = { Text("\"${uiState.fileToDelete?.name}\" tas bort permanent.") },
            confirmButton = {
                TextButton(onClick = viewModel::confirmDelete) {
                    Text("Ta bort", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = viewModel::cancelDelete) { Text("Avbryt") }
            }
        )
    }

    // Rename dialog
    if (uiState.showRenameDialog) {
        var newName by remember(uiState.fileToRename) {
            mutableStateOf(uiState.fileToRename?.name ?: "")
        }
        AlertDialog(
            onDismissRequest = viewModel::cancelRename,
            title = { Text("Byt namn") },
            text = {
                OutlinedTextField(
                    value = newName,
                    onValueChange = { newName = it },
                    label = { Text("Filnamn") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            },
            confirmButton = {
                Button(
                    onClick = { if (newName.isNotBlank()) viewModel.confirmRename(newName) },
                    enabled = newName.isNotBlank()
                ) { Text("Spara") }
            },
            dismissButton = {
                TextButton(onClick = viewModel::cancelRename) { Text("Avbryt") }
            }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ProjectFileItem(
    file: ProjectFile,
    onTap: () -> Unit,
    onLongPress: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 2.dp)
            .combinedClickable(onClick = onTap, onLongClick = onLongPress)
    ) {
        ListItem(
            headlineContent = {
                Text(file.name, fontWeight = FontWeight.Medium, maxLines = 1)
            },
            supportingContent = {
                Text(
                    "${formatFileSize(file.sizeBytes)}  ·  ${formatDate(file.addedAt)}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.outline
                )
            },
            leadingContent = {
                Surface(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = MaterialTheme.shapes.small
                ) {
                    Box(Modifier.size(40.dp), contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = fileTypeIcon(file.mimeType),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            },
            trailingContent = {
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Ta bort",
                        tint = MaterialTheme.colorScheme.outline)
                }
            }
        )
    }
}

private fun fileTypeIcon(mimeType: String) = when {
    mimeType == "application/pdf" -> Icons.Default.PictureAsPdf
    mimeType.startsWith("image/") -> Icons.Default.Image
    else -> Icons.Default.AttachFile
}

private fun formatFileSize(bytes: Long): String = when {
    bytes < 1024L -> "$bytes B"
    bytes < 1024L * 1024L -> "${bytes / 1024} KB"
    else -> String.format("%.1f MB", bytes / (1024.0 * 1024.0))
}

private fun formatDate(millis: Long): String =
    SimpleDateFormat("d MMM yyyy", Locale("sv")).format(Date(millis))
