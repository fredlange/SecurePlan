package se.secureplan.app.feature.photos

import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import se.secureplan.app.core.domain.model.GeoPhoto
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GeoPhotoDetailScreen(
    photoId: String,
    onBack: () -> Unit,
    viewModel: GeoPhotoViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val photo = uiState.photos.find { it.id == photoId }

    // Pinch-to-zoom state
    var scale by remember { mutableFloatStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    val transformableState = rememberTransformableState { zoomChange, panChange, _ ->
        scale = (scale * zoomChange).coerceIn(0.5f, 5f)
        offset += panChange
    }

    // Caption edit state
    var caption by remember(photo?.caption) { mutableStateOf(photo?.caption ?: "") }
    var captionEditing by remember { mutableStateOf(false) }

    // Reset zoom on double-tap via counter
    var resetZoom by remember { mutableIntStateOf(0) }
    LaunchedEffect(resetZoom) {
        if (resetZoom > 0) {
            scale = 1f
            offset = Offset.Zero
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Fotodetail", fontWeight = FontWeight.Bold) },
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
                    if (photo != null) {
                        IconButton(onClick = { resetZoom++ }) {
                            Icon(Icons.Default.FitScreen, contentDescription = "Återställ zoom",
                                tint = MaterialTheme.colorScheme.onPrimary)
                        }
                    }
                }
            )
        }
    ) { padding ->
        if (photo == null) {
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                Text("Foto hittades inte", color = MaterialTheme.colorScheme.outline)
            }
            return@Scaffold
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // ── Zoomable image ───────────────────────────────────────────────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = photo.photoUri,
                    contentDescription = photo.caption.ifBlank { "Foto" },
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer(
                            scaleX = scale,
                            scaleY = scale,
                            translationX = offset.x,
                            translationY = offset.y
                        )
                        .transformable(transformableState),
                    contentScale = ContentScale.Fit
                )

                // Zoom hint
                if (abs(scale - 1f) < 0.05f) {
                    Surface(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 8.dp),
                        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f),
                        shape = MaterialTheme.shapes.small
                    ) {
                        Text(
                            "Nyp för att zooma",
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                }
            }

            // ── Info bottom sheet ────────────────────────────────────────────
            Surface(
                tonalElevation = 2.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Caption row
                    if (captionEditing) {
                        OutlinedTextField(
                            value = caption,
                            onValueChange = { caption = it },
                            label = { Text("Bildtext") },
                            modifier = Modifier.fillMaxWidth(),
                            trailingIcon = {
                                Row {
                                    IconButton(onClick = {
                                        viewModel.updateCaption(photo, caption)
                                        captionEditing = false
                                    }) {
                                        Icon(Icons.Default.Check, contentDescription = "Spara",
                                            tint = MaterialTheme.colorScheme.primary)
                                    }
                                    IconButton(onClick = {
                                        caption = photo.caption
                                        captionEditing = false
                                    }) {
                                        Icon(Icons.Default.Close, contentDescription = "Avbryt",
                                            tint = MaterialTheme.colorScheme.outline)
                                    }
                                }
                            },
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                            singleLine = true
                        )
                    } else {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text("Bildtext", style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.outline)
                                Text(
                                    caption.ifBlank { "Ingen bildtext" },
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = if (caption.isBlank())
                                        MaterialTheme.colorScheme.outline
                                    else
                                        MaterialTheme.colorScheme.onSurface
                                )
                            }
                            IconButton(onClick = { captionEditing = true }) {
                                Icon(Icons.Default.Edit, contentDescription = "Redigera bildtext",
                                    tint = MaterialTheme.colorScheme.primary)
                            }
                        }
                    }

                    HorizontalDivider()

                    // GPS coordinates
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(Icons.Default.LocationOn,
                            contentDescription = null,
                            tint = if (photo.latitude != null)
                                MaterialTheme.colorScheme.primary
                            else
                                MaterialTheme.colorScheme.outline,
                            modifier = Modifier.size(18.dp))
                        Column {
                            Text("GPS-koordinater", style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.outline)
                            Text(
                                if (photo.latitude != null && photo.longitude != null)
                                    formatGps(photo.latitude, photo.longitude)
                                else "Ingen GPS-data",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }

                    HorizontalDivider()

                    // Date taken
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(Icons.Default.Schedule, contentDescription = null,
                            tint = MaterialTheme.colorScheme.outline,
                            modifier = Modifier.size(18.dp))
                        Column {
                            Text("Datum", style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.outline)
                            Text(
                                remember(photo.takenAt) {
                                    SimpleDateFormat("d MMMM yyyy, HH:mm",
                                        Locale("sv", "SE")).format(Date(photo.takenAt))
                                },
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }

                    HorizontalDivider()

                    // Disabled "Placera på ritning" button
                    Button(
                        onClick = { /* TODO: Fas 6 */ },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = false,
                        colors = ButtonDefaults.buttonColors(
                            disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Icon(Icons.Default.Map, contentDescription = null,
                            modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(8.dp))
                        Text("Placera på ritning (ej tillgängligt än)")
                    }

                    Spacer(Modifier.height(8.dp))
                }
            }
        }
    }
}

/** Format decimal degrees to DMS notation: 59°20'N, 18°03'E */
private fun formatGps(lat: Double, lon: Double): String {
    fun toDms(dd: Double): Triple<Int, Int, Double> {
        val abs = kotlin.math.abs(dd)
        val d = abs.toInt()
        val mFull = (abs - d) * 60
        val m = mFull.toInt()
        val s = (mFull - m) * 60
        return Triple(d, m, s)
    }
    val (latD, latM, _) = toDms(lat)
    val (lonD, lonM, _) = toDms(lon)
    val latRef = if (lat >= 0) "N" else "S"
    val lonRef = if (lon >= 0) "E" else "W"
    return "%d°%02d'%s, %d°%02d'%s".format(latD, latM, latRef, lonD, lonM, lonRef)
}
