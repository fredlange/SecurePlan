package se.secureplan.app.feature.photos

import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import se.secureplan.app.core.domain.model.GeoPhoto
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GeoPhotoScreen(
    projectId: String,
    onBack: () -> Unit,
    onPhotoClick: (String) -> Unit,
    viewModel: GeoPhotoViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    var fabExpanded by remember { mutableStateOf(false) }
    var pendingCameraUri by remember { mutableStateOf<Uri?>(null) }
    var showDeleteDialog by remember { mutableStateOf<GeoPhoto?>(null) }

    LaunchedEffect(projectId) { viewModel.loadPhotos(projectId) }

    // Camera result — fires after TakePicture completes
    val cameraLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            pendingCameraUri?.let { uri ->
                viewModel.savePhotoFromUri(projectId, uri)
            }
        }
        pendingCameraUri = null
    }

    // Gallery picker
    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        uri?.let { viewModel.savePhotoFromUri(projectId, it) }
    }

    // Camera permission
    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            val uri = createCameraUri(context)
            pendingCameraUri = uri
            cameraLauncher.launch(uri)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Foton", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Tillbaka",
                            tint = MaterialTheme.colorScheme.onPrimary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                actions = {
                    Text(
                        "${uiState.photos.size} foto${if (uiState.photos.size != 1) "n" else ""}",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f),
                        modifier = Modifier.padding(end = 12.dp)
                    )
                }
            )
        },
        floatingActionButton = {
            Column(horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.spacedBy(12.dp)) {
                // Mini FABs shown when expanded
                if (fabExpanded) {
                    // Gallery option
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Surface(
                            color = MaterialTheme.colorScheme.surface,
                            shape = MaterialTheme.shapes.medium,
                            tonalElevation = 4.dp
                        ) {
                            Text("Välj från galleri",
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                style = MaterialTheme.typography.labelMedium)
                        }
                        SmallFloatingActionButton(
                            onClick = {
                                fabExpanded = false
                                galleryLauncher.launch(
                                    PickVisualMediaRequest(
                                        ActivityResultContracts.PickVisualMedia.ImageOnly
                                    )
                                )
                            },
                            containerColor = MaterialTheme.colorScheme.secondaryContainer
                        ) {
                            Icon(Icons.Default.PhotoLibrary, contentDescription = "Galleri")
                        }
                    }

                    // Camera option
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Surface(
                            color = MaterialTheme.colorScheme.surface,
                            shape = MaterialTheme.shapes.medium,
                            tonalElevation = 4.dp
                        ) {
                            Text("Ta foto",
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                style = MaterialTheme.typography.labelMedium)
                        }
                        SmallFloatingActionButton(
                            onClick = {
                                fabExpanded = false
                                if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                                    == PackageManager.PERMISSION_GRANTED
                                ) {
                                    val uri = createCameraUri(context)
                                    pendingCameraUri = uri
                                    cameraLauncher.launch(uri)
                                } else {
                                    cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                                }
                            },
                            containerColor = MaterialTheme.colorScheme.secondaryContainer
                        ) {
                            Icon(Icons.Default.CameraAlt, contentDescription = "Kamera")
                        }
                    }
                }

                // Main FAB
                FloatingActionButton(
                    onClick = { fabExpanded = !fabExpanded }
                ) {
                    Icon(
                        if (fabExpanded) Icons.Default.Close else Icons.Default.Add,
                        contentDescription = "Lägg till foto"
                    )
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

        if (uiState.photos.isEmpty()) {
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.PhotoCamera, contentDescription = null,
                        modifier = Modifier.size(64.dp), tint = MaterialTheme.colorScheme.outline)
                    Spacer(Modifier.height(16.dp))
                    Text("Inga foton ännu", style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.outline)
                    Text("Lägg till foton med knappen nedan",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.outline)
                }
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize().padding(padding),
                contentPadding = PaddingValues(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(uiState.photos, key = { it.id }) { photo ->
                    PhotoGridItem(
                        photo = photo,
                        onClick = { onPhotoClick(photo.id) },
                        onLongClick = { showDeleteDialog = photo }
                    )
                }
            }
        }
    }

    // Close FAB overlay when tapping outside
    if (fabExpanded) {
        androidx.compose.foundation.layout.Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp)
        )
    }

    // Delete confirmation
    showDeleteDialog?.let { photo ->
        AlertDialog(
            onDismissRequest = { showDeleteDialog = null },
            title = { Text("Ta bort foto?") },
            text = { Text("Bilden tas bort permanent från projektet.") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.deletePhoto(photo.id)
                    showDeleteDialog = null
                }) {
                    Text("Ta bort", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = null }) { Text("Avbryt") }
            }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun PhotoGridItem(
    photo: GeoPhoto,
    onClick: () -> Unit,
    onLongClick: () -> Unit
) {
    val dateStr = remember(photo.takenAt) {
        SimpleDateFormat("dd MMM", Locale("sv", "SE")).format(Date(photo.takenAt))
    }
    Card(
        modifier = Modifier
            .aspectRatio(1f)
            .combinedClickable(onClick = onClick, onLongClick = onLongClick),
        shape = MaterialTheme.shapes.medium
    ) {
        Box(Modifier.fillMaxSize()) {
            AsyncImage(
                model = photo.photoUri,
                contentDescription = photo.caption.ifBlank { "Foto" },
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            // GPS badge
            if (photo.latitude != null) {
                Surface(
                    modifier = Modifier.align(Alignment.TopEnd).padding(4.dp),
                    color = MaterialTheme.colorScheme.primary,
                    shape = MaterialTheme.shapes.extraSmall
                ) {
                    Icon(
                        Icons.Default.LocationOn,
                        contentDescription = "GPS",
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(16.dp).padding(2.dp)
                    )
                }
            }
            // Date + caption overlay
            Surface(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth(),
                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.75f),
                shape = MaterialTheme.shapes.extraSmall
            ) {
                Column(modifier = Modifier.padding(4.dp)) {
                    if (photo.caption.isNotBlank()) {
                        Text(photo.caption, style = MaterialTheme.typography.labelSmall,
                            maxLines = 1, fontWeight = FontWeight.Medium)
                    }
                    Text(dateStr, style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.outline)
                }
            }
        }
    }
}

private fun createCameraUri(context: android.content.Context): Uri {
    val contentValues = ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME,
            "securePlan_${System.currentTimeMillis()}.jpg")
        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/SecurePlan")
        }
    }
    return context.contentResolver.insert(
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        contentValues
    ) ?: Uri.EMPTY
}
