package se.secureplan.app.feature.drawing

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import se.secureplan.app.core.domain.model.*
import kotlin.math.sqrt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawingScreen(
    drawingId: String,
    onBack: () -> Unit,
    viewModel: DrawingViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    val backgroundImageLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri -> uri?.let { viewModel.setBackgroundImage(it) } }

    val backgroundPdfLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri -> uri?.let { viewModel.setBackgroundPdf(it) } }

    // ─── Photo marker launchers ──────────────────────────────────────────
    var pendingPhotoMarkerCameraUri by remember { mutableStateOf<Uri?>(null) }

    val photoMarkerCameraLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) pendingPhotoMarkerCameraUri?.let { viewModel.placePhotoMarker(it) }
        pendingPhotoMarkerCameraUri = null
    }

    val photoMarkerGalleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri -> uri?.let { viewModel.placePhotoMarker(it) } }

    val photoMarkerCameraPermLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            val uri = createCameraUri(context)
            pendingPhotoMarkerCameraUri = uri
            photoMarkerCameraLauncher.launch(uri)
        }
    }

    LaunchedEffect(drawingId) { viewModel.loadDrawing(drawingId) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(uiState.drawing?.name ?: "Drawing", fontWeight = FontWeight.Bold) },
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
                    IconButton(onClick = viewModel::showBackgroundPicker) {
                        Icon(Icons.Default.Attachment, contentDescription = "Välj underlag",
                            tint = MaterialTheme.colorScheme.onPrimary)
                    }
                    IconButton(onClick = { viewModel.toggleLayerPanel() }) {
                        Icon(Icons.Default.Layers, contentDescription = "Layers",
                            tint = MaterialTheme.colorScheme.onPrimary)
                    }
                }
            )
        },
        bottomBar = {
            DrawingToolbar(
                activeTool = uiState.activeTool,
                onToolSelected = viewModel::selectTool,
                onSymbolPickerOpen = {
                    viewModel.selectTool(DrawingTool.PLACE_SYMBOL)
                    viewModel.showSymbolPicker()
                },
                onFinishCable = { viewModel.finishCable() },
                onFinishZone  = { viewModel.finishZone() },
                cableInProgress = uiState.cableInProgress.isNotEmpty(),
                zoneInProgress  = uiState.zoneInProgress.isNotEmpty()
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            if (uiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                DrawingCanvas(
                    uiState = uiState,
                    onTap = { x, y -> viewModel.onCanvasTap(x, y) },
                    onTransform = { scale, ox, oy -> viewModel.updateTransform(scale, ox, oy) },
                    onDeletePlacement = viewModel::deletePlacement,
                    onDeleteCable = viewModel::deleteCable,
                    onDeleteZone = viewModel::deleteZone,
                    onLongPressPlacement = viewModel::showPlacementContextMenu
                )
            }

            // Layer panel overlay
            if (uiState.showLayerPanel) {
                LayerPanel(
                    visibleLayers = uiState.visibleLayers,
                    isBackgroundVisible = uiState.isBackgroundVisible,
                    layerOrder = uiState.layerOrder,
                    onToggle = viewModel::toggleLayerVisibility,
                    onToggleBackground = viewModel::toggleBackgroundVisibility,
                    onMoveUp = viewModel::moveLayerUp,
                    onMoveDown = viewModel::moveLayerDown,
                    onClose = viewModel::toggleLayerPanel,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                )
            }
        }
    }

    // Background type picker dialog
    if (uiState.showBackgroundTypePicker) {
        AlertDialog(
            onDismissRequest = viewModel::hideBackgroundPicker,
            title = { Text("Välj underlag") },
            text = { Text("Välj ett foto/bild från galleriet eller ett PDF-dokument som ska visas som ritningsunderlag.") },
            confirmButton = {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedButton(onClick = {
                        viewModel.hideBackgroundPicker()
                        backgroundImageLauncher.launch("image/*")
                    }) {
                        Icon(Icons.Default.Image, contentDescription = null)
                        Spacer(Modifier.width(4.dp))
                        Text("Foto / Bild")
                    }
                    Button(onClick = {
                        viewModel.hideBackgroundPicker()
                        backgroundPdfLauncher.launch("application/pdf")
                    }) {
                        Icon(Icons.Default.PictureAsPdf, contentDescription = null)
                        Spacer(Modifier.width(4.dp))
                        Text("PDF")
                    }
                }
            },
            dismissButton = {
                TextButton(onClick = viewModel::hideBackgroundPicker) { Text("Avbryt") }
            }
        )
    }

    // PDF page picker dialog (multi-page PDFs)
    if (uiState.showPdfPagePicker) {
        AlertDialog(
            onDismissRequest = viewModel::hidePdfPagePicker,
            title = { Text("Välj sida") },
            text = {
                Column {
                    Text("Dokumentet har ${uiState.pdfPageCount} sidor. Välj vilken sida som ska visas som underlag.")
                    Spacer(Modifier.height(8.dp))
                    LazyColumn(modifier = Modifier.heightIn(max = 320.dp)) {
                        items(uiState.pdfPageCount) { index ->
                            TextButton(
                                onClick = { viewModel.selectPdfPage(index) },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "Sida ${index + 1}",
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                    }
                }
            },
            confirmButton = {},
            dismissButton = {
                TextButton(onClick = viewModel::hidePdfPagePicker) { Text("Avbryt") }
            }
        )
    }

    // Photo marker source picker dialog
    if (uiState.showPhotoMarkerPicker) {
        AlertDialog(
            onDismissRequest = viewModel::cancelPhotoMarkerPicker,
            title = { Text("Infoga platsbild") },
            text = { Text("Välj en bild från galleriet eller ta ett nytt foto. Bilden placeras som en markör på ritningen.") },
            confirmButton = {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedButton(onClick = {
                        viewModel.hidePhotoMarkerPicker()
                        photoMarkerGalleryLauncher.launch("image/*")
                    }) {
                        Icon(Icons.Default.Photo, contentDescription = null)
                        Spacer(Modifier.width(4.dp))
                        Text("Galleri")
                    }
                    Button(onClick = {
                        viewModel.hidePhotoMarkerPicker()
                        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                            == PackageManager.PERMISSION_GRANTED) {
                            val uri = createCameraUri(context)
                            pendingPhotoMarkerCameraUri = uri
                            photoMarkerCameraLauncher.launch(uri)
                        } else {
                            photoMarkerCameraPermLauncher.launch(Manifest.permission.CAMERA)
                        }
                    }) {
                        Icon(Icons.Default.CameraAlt, contentDescription = null)
                        Spacer(Modifier.width(4.dp))
                        Text("Kamera")
                    }
                }
            },
            dismissButton = {
                TextButton(onClick = viewModel::cancelPhotoMarkerPicker) { Text("Avbryt") }
            }
        )
    }

    // Symbol picker bottom sheet
    if (uiState.showSymbolPicker) {
        SymbolPickerSheet(
            symbols = uiState.symbols,
            onSymbolSelected = viewModel::selectSymbol,
            onDismiss = viewModel::hideSymbolPicker
        )
    }

    // Product picker bottom sheet (shown after symbol selection or via context menu)
    if (uiState.showProductPicker) {
        ProductPickerSheet(
            products = uiState.products,
            onProductSelected = viewModel::selectProductForPicker,
            onDismiss = viewModel::hideProductPicker
        )
    }

    // Placement context menu (long-press on existing symbol)
    if (uiState.showPlacementMenu) {
        val pid = uiState.menuPlacementId
        val currentProduct = uiState.placements.find { it.id == pid }
            ?.productId?.let { id -> uiState.products.find { it.id == id } }
        AlertDialog(
            onDismissRequest = viewModel::hidePlacementContextMenu,
            title = { Text("Markering") },
            text = {
                Column {
                    if (currentProduct != null) {
                        Text("Produkt: ${currentProduct.manufacturer} ${currentProduct.name}")
                    } else {
                        Text("Ingen produkt tilldelad")
                    }
                }
            },
            confirmButton = {
                Button(onClick = { pid?.let { viewModel.openProductPickerForExisting(it) } }) {
                    Text("Tilldela produkt")
                }
            },
            dismissButton = {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    if (currentProduct != null) {
                        OutlinedButton(onClick = {
                            pid?.let { viewModel.bindProductToPlacement(it, null) }
                        }) { Text("Ta bort produkt") }
                    }
                    TextButton(onClick = viewModel::hidePlacementContextMenu) { Text("Stäng") }
                }
            }
        )
    }
}

// ─── Canvas ──────────────────────────────────────────────────────────────────

@Composable
private fun DrawingCanvas(
    uiState: DrawingUiState,
    onTap: (Float, Float) -> Unit,
    onTransform: (Float, Float, Float) -> Unit,
    onDeletePlacement: (String) -> Unit,
    onDeleteCable: (String) -> Unit,
    onDeleteZone: (String) -> Unit,
    onLongPressPlacement: (String) -> Unit
) {
    var scale by remember { mutableStateOf(uiState.scale) }
    var offsetX by remember { mutableStateOf(uiState.offsetX) }
    var offsetY by remember { mutableStateOf(uiState.offsetY) }

    val transformableState = rememberTransformableState { zoomChange, panChange, _ ->
        scale = (scale * zoomChange).coerceIn(0.5f, 10f)
        offsetX += panChange.x
        offsetY += panChange.y
        onTransform(scale, offsetX, offsetY)
    }

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .transformable(transformableState)
            .pointerInput(uiState.activeTool) {
                val currentPlacements = uiState.placements
                detectTapGestures(
                    onTap = { tapOffset ->
                        // Convert screen coordinates to normalized canvas coordinates
                        val canvasW = size.width.toFloat()
                        val canvasH = size.height.toFloat()
                        val xNorm = ((tapOffset.x - offsetX) / (canvasW * scale)).coerceIn(0f, 1f)
                        val yNorm = ((tapOffset.y - offsetY) / (canvasH * scale)).coerceIn(0f, 1f)
                        onTap(xNorm, yNorm)
                    },
                    onLongPress = { pressOffset ->
                        // Hit-test placements: find closest within 60px threshold (screen space)
                        val canvasW = size.width.toFloat()
                        val canvasH = size.height.toFloat()
                        val xNorm = ((pressOffset.x - offsetX) / (canvasW * scale)).coerceIn(0f, 1f)
                        val yNorm = ((pressOffset.y - offsetY) / (canvasH * scale)).coerceIn(0f, 1f)
                        val hitRadius = 60f / (canvasW * scale)
                        val hit = currentPlacements.minByOrNull { placement ->
                            val dx = placement.xNorm - xNorm
                            val dy = placement.yNorm - yNorm
                            dx * dx + dy * dy
                        }?.takeIf { placement ->
                            val dx = placement.xNorm - xNorm
                            val dy = placement.yNorm - yNorm
                            (dx * dx + dy * dy) <= hitRadius * hitRadius
                        }
                        hit?.let { onLongPressPlacement(it.id) }
                    }
                )
            }
    ) {
        val canvasW = size.width
        val canvasH = size.height

        // Apply pan/zoom transform
        drawContext.transform.translate(offsetX, offsetY)
        drawContext.transform.scale(scale, scale)

        // Grid is always at the absolute bottom
        val bgShowing = uiState.isBackgroundVisible && uiState.backgroundBitmap != null
        drawGrid(canvasW, canvasH, subtle = bgShowing)

        // Draw layers in their defined order (first = behind, last = on top)
        uiState.layerOrder.forEach { drawableLayer ->
            when (drawableLayer) {
                is DrawableLayer.Background -> {
                    if (uiState.isBackgroundVisible) {
                        uiState.backgroundBitmap?.let { bitmap ->
                            drawImage(
                                image = bitmap,
                                dstOffset = IntOffset.Zero,
                                dstSize = IntSize(canvasW.toInt(), canvasH.toInt()),
                                filterQuality = FilterQuality.Medium
                            )
                        }
                    }
                }
                is DrawableLayer.SystemLayer -> {
                    if (drawableLayer.type in uiState.visibleLayers) {
                        if (drawableLayer.type == LayerType.DETECTION) {
                            uiState.zones.filter { it.isVisible }.forEach { zone ->
                                drawZone(zone, canvasW, canvasH)
                            }
                        }
                        uiState.cables
                            .filter { it.layerType == drawableLayer.type && it.isVisible }
                            .forEach { cable -> drawCablePath(cable, canvasW, canvasH) }
                        uiState.placements
                            .filter { it.layerType == drawableLayer.type && it.isVisible }
                            .forEach { placement -> drawSymbolMarker(placement, canvasW, canvasH) }
                    }
                }
            }
        }

        // In-progress elements always rendered above layer content
        if (uiState.zoneInProgress.size >= 2)  drawInProgressPolygon(uiState.zoneInProgress, canvasW, canvasH)
        if (uiState.cableInProgress.size >= 2) drawInProgressLine(uiState.cableInProgress, canvasW, canvasH)

        // Photo markers always on top of everything
        uiState.photoMarkers.filter { it.xNorm != null && it.yNorm != null }.forEach { photo ->
            drawPhotoMarker(photo, uiState.photoThumbnails[photo.id], canvasW, canvasH)
        }
    }
}

private fun DrawScope.drawGrid(w: Float, h: Float, subtle: Boolean = false) {
    val gridSpacing = 50f
    val gridColor = if (subtle) Color(0x11000000) else Color(0x22000000)
    var x = 0f
    while (x <= w) {
        drawLine(gridColor, Offset(x, 0f), Offset(x, h), strokeWidth = 1f)
        x += gridSpacing
    }
    var y = 0f
    while (y <= h) {
        drawLine(gridColor, Offset(0f, y), Offset(w, y), strokeWidth = 1f)
        y += gridSpacing
    }
}

private fun DrawScope.drawZone(zone: Zone, w: Float, h: Float) {
    if (zone.polygon.size < 3) return
    val pts = zone.polygon.map { Offset(it.x * w, it.y * h) }
    val fillColor = try { Color(android.graphics.Color.parseColor(zone.fillColorHex)) } catch (_: Exception) { Color.Blue }
    val strokeColor = try { Color(android.graphics.Color.parseColor(zone.strokeColorHex)) } catch (_: Exception) { Color.DarkGray }
    val path = Path().apply {
        moveTo(pts[0].x, pts[0].y)
        pts.drop(1).forEach { lineTo(it.x, it.y) }
        close()
    }
    drawPath(path, fillColor.copy(alpha = zone.fillAlpha))
    drawPath(path, strokeColor, style = Stroke(width = 2f))
}

private fun DrawScope.drawInProgressPolygon(points: List<NormPoint>, w: Float, h: Float) {
    val pts = points.map { Offset(it.x * w, it.y * h) }
    val path = Path().apply {
        moveTo(pts[0].x, pts[0].y)
        pts.drop(1).forEach { lineTo(it.x, it.y) }
    }
    drawPath(path, Color(0x553F51B5), style = Stroke(width = 3f, pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 5f))))
    pts.forEach { drawCircle(Color(0xFF3F51B5), radius = 6f, center = it) }
}

private fun DrawScope.drawCablePath(cable: CablePath, w: Float, h: Float) {
    if (cable.points.size < 2) return
    val pts = cable.points.map { Offset(it.x * w, it.y * h) }
    val color = try { Color(android.graphics.Color.parseColor(cable.colorHex)) } catch (_: Exception) { Color.Red }
    val path = Path().apply {
        moveTo(pts[0].x, pts[0].y)
        pts.drop(1).forEach { lineTo(it.x, it.y) }
    }
    drawPath(path, color, style = Stroke(width = cable.strokeWidth))
}

private fun DrawScope.drawInProgressLine(points: List<NormPoint>, w: Float, h: Float) {
    val pts = points.map { Offset(it.x * w, it.y * h) }
    val path = Path().apply {
        moveTo(pts[0].x, pts[0].y)
        pts.drop(1).forEach { lineTo(it.x, it.y) }
    }
    drawPath(path, Color(0xFFFF5722), style = Stroke(width = 4f, pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 5f))))
    pts.forEach { drawCircle(Color(0xFFFF5722), radius = 5f, center = it) }
}

private fun DrawScope.drawSymbolMarker(placement: SymbolPlacement, w: Float, h: Float) {
    val center = Offset(placement.xNorm * w, placement.yNorm * h)
    val color = when (placement.layerType) {
        LayerType.DETECTION -> Color(0xFF1565C0)
        LayerType.POWER     -> Color(0xFFF9A825)
        LayerType.DATA      -> Color(0xFF2E7D32)
        LayerType.CONTROL   -> Color(0xFF6A1B9A)
        LayerType.STRUCTURE -> Color(0xFF4E342E)
    }
    drawCircle(color.copy(alpha = 0.2f), radius = 18f, center = center)
    drawCircle(color, radius = 18f, center = center, style = Stroke(width = 2f))
    drawCircle(color, radius = 5f, center = center)
    // Green badge when a product is assigned
    if (placement.productId != null) {
        val badgeCenter = Offset(center.x + 13f, center.y - 13f)
        drawCircle(Color(0xFF2E7D32), radius = 6f, center = badgeCenter)
        drawCircle(Color.White, radius = 6f, center = badgeCenter, style = Stroke(width = 1.5f))
    }
}

// ─── Bottom toolbar ───────────────────────────────────────────────────────────

@Composable
private fun DrawingToolbar(
    activeTool: DrawingTool,
    onToolSelected: (DrawingTool) -> Unit,
    onSymbolPickerOpen: () -> Unit,
    onFinishCable: () -> Unit,
    onFinishZone: () -> Unit,
    cableInProgress: Boolean,
    zoneInProgress: Boolean
) {
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 4.dp
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ToolButton(Icons.Default.PanTool, "Välj",
                activeTool == DrawingTool.SELECT) { onToolSelected(DrawingTool.SELECT) }
            ToolButton(Icons.Default.AddCircle, "Symbol",
                activeTool == DrawingTool.PLACE_SYMBOL) { onSymbolPickerOpen() }
            ToolButton(Icons.Default.Timeline, "Kabel",
                activeTool == DrawingTool.DRAW_CABLE) {
                if (cableInProgress) onFinishCable()
                else onToolSelected(DrawingTool.DRAW_CABLE)
            }
            ToolButton(Icons.Default.CropFree, "Zon",
                activeTool == DrawingTool.DRAW_ZONE) {
                if (zoneInProgress) onFinishZone()
                else onToolSelected(DrawingTool.DRAW_ZONE)
            }
            ToolButton(Icons.Default.AddAPhoto, "Foto",
                activeTool == DrawingTool.PLACE_PHOTO) { onToolSelected(DrawingTool.PLACE_PHOTO) }
            ToolButton(Icons.Default.DeleteOutline, "Radera",
                activeTool == DrawingTool.ERASE) { onToolSelected(DrawingTool.ERASE) }
        }
    }
}

@Composable
private fun ToolButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    isActive: Boolean,
    onClick: () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        IconButton(onClick = onClick) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = if (isActive) MaterialTheme.colorScheme.primary
                       else MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = if (isActive) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

// ─── Layer panel ──────────────────────────────────────────────────────────────

@Composable
private fun LayerPanel(
    visibleLayers: Set<LayerType>,
    isBackgroundVisible: Boolean,
    layerOrder: List<DrawableLayer>,
    onToggle: (LayerType) -> Unit,
    onToggleBackground: () -> Unit,
    onMoveUp: (DrawableLayer) -> Unit,
    onMoveDown: (DrawableLayer) -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier, elevation = CardDefaults.cardElevation(8.dp)) {
        Column(modifier = Modifier.padding(12.dp).width(230.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Lager", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f))
                IconButton(onClick = onClose, modifier = Modifier.size(24.dp)) {
                    Icon(Icons.Default.Close, contentDescription = "Stäng", modifier = Modifier.size(16.dp))
                }
            }
            HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
            // Display layers in reversed order: top of panel = rendered on top
            val reversed = layerOrder.reversed()
            reversed.forEachIndexed { panelIdx, layer ->
                val isVisible = when (layer) {
                    is DrawableLayer.Background -> isBackgroundVisible
                    is DrawableLayer.SystemLayer -> layer.type in visibleLayers
                }
                LayerRow(
                    layer = layer,
                    isVisible = isVisible,
                    onToggleVisibility = {
                        when (layer) {
                            is DrawableLayer.Background -> onToggleBackground()
                            is DrawableLayer.SystemLayer -> onToggle(layer.type)
                        }
                    },
                    canMoveUp   = panelIdx > 0,
                    canMoveDown = panelIdx < reversed.lastIndex,
                    onMoveUp    = { onMoveUp(layer) },
                    onMoveDown  = { onMoveDown(layer) }
                )
            }
        }
    }
}

@Composable
private fun LayerRow(
    layer: DrawableLayer,
    isVisible: Boolean,
    onToggleVisibility: () -> Unit,
    canMoveUp: Boolean,
    canMoveDown: Boolean,
    onMoveUp: () -> Unit,
    onMoveDown: () -> Unit
) {
    val label = when (layer) {
        is DrawableLayer.Background -> "Bakgrund"
        is DrawableLayer.SystemLayer -> when (layer.type) {
            LayerType.STRUCTURE  -> "Struktur"
            LayerType.POWER      -> "El/Kraft"
            LayerType.DATA       -> "Data/Nätverk"
            LayerType.DETECTION  -> "Detektering"
            LayerType.CONTROL    -> "Styrning"
        }
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().height(40.dp)
    ) {
        Checkbox(
            checked = isVisible,
            onCheckedChange = { onToggleVisibility() },
            modifier = Modifier.size(36.dp)
        )
        Text(label, style = MaterialTheme.typography.bodySmall, modifier = Modifier.weight(1f))
        IconButton(
            onClick = onMoveUp,
            enabled = canMoveUp,
            modifier = Modifier.size(28.dp)
        ) {
            Icon(Icons.Default.KeyboardArrowUp, contentDescription = "Flytta upp",
                modifier = Modifier.size(18.dp),
                tint = if (canMoveUp) MaterialTheme.colorScheme.onSurface
                       else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
        }
        IconButton(
            onClick = onMoveDown,
            enabled = canMoveDown,
            modifier = Modifier.size(28.dp)
        ) {
            Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Flytta ner",
                modifier = Modifier.size(18.dp),
                tint = if (canMoveDown) MaterialTheme.colorScheme.onSurface
                       else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
        }
    }
}

// ─── Symbol picker bottom sheet ───────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SymbolPickerSheet(
    symbols: List<Symbol>,
    onSymbolSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(modifier = Modifier.padding(bottom = 32.dp)) {
            Text("Choose Symbol",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp))

            if (symbols.isEmpty()) {
                Box(Modifier.fillMaxWidth().padding(32.dp), contentAlignment = Alignment.Center) {
                    Text("No symbols loaded", color = MaterialTheme.colorScheme.outline)
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.heightIn(max = 400.dp)
                ) {
                    items(symbols, key = { it.id }) { symbol ->
                        SymbolChip(symbol = symbol, onClick = { onSymbolSelected(symbol.id) })
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SymbolChip(symbol: Symbol, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().aspectRatio(1f)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(Icons.Default.Circle, contentDescription = null,
                tint = Color(symbol.color).let { c ->
                    try { c } catch (_: Exception) { MaterialTheme.colorScheme.primary }
                },
                modifier = Modifier.size(28.dp))
            Spacer(Modifier.height(4.dp))
            Text(symbol.name, style = MaterialTheme.typography.labelSmall,
                maxLines = 2, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
        }
    }
}

// ─── Photo marker helpers ─────────────────────────────────────────────────────

private fun DrawScope.drawPhotoMarker(photo: GeoPhoto, thumbnail: ImageBitmap?, w: Float, h: Float) {
    val cx = (photo.xNorm ?: return) * w
    val cy = (photo.yNorm ?: return) * h
    val center = Offset(cx, cy)
    val markerColor = Color(0xFFE65100) // deep orange — distinct from symbol markers

    if (thumbnail != null) {
        // Draw thumbnail as a small square with an orange border
        val half = 36f
        drawImage(
            image = thumbnail,
            dstOffset = IntOffset((cx - half).toInt(), (cy - half).toInt()),
            dstSize   = IntSize((half * 2).toInt(), (half * 2).toInt()),
            filterQuality = FilterQuality.Low
        )
        drawRect(
            color   = markerColor,
            topLeft = Offset(cx - half, cy - half),
            size    = androidx.compose.ui.geometry.Size(half * 2, half * 2),
            style   = Stroke(width = 3f)
        )
    } else {
        // Camera icon: circle background + simplified camera shape
        drawCircle(markerColor.copy(alpha = 0.15f), radius = 26f, center = center)
        drawCircle(markerColor, radius = 26f, center = center, style = Stroke(width = 2.5f))
        // Camera body
        drawRect(
            color   = markerColor,
            topLeft = Offset(cx - 11f, cy - 7f),
            size    = androidx.compose.ui.geometry.Size(22f, 14f),
            style   = Stroke(width = 1.8f)
        )
        // Lens circle
        drawCircle(markerColor, radius = 5f, center = center, style = Stroke(width = 1.8f))
        // Viewfinder bump at top
        drawRect(
            color   = markerColor,
            topLeft = Offset(cx - 5f, cy - 14f),
            size    = androidx.compose.ui.geometry.Size(10f, 7f),
            style   = Stroke(width = 1.8f)
        )
    }
}

/** Creates a MediaStore URI for a new camera photo in Pictures/SecurePlan. */
private fun createCameraUri(context: Context): Uri {
    val values = ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME, "securePlan_${System.currentTimeMillis()}.jpg")
        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/SecurePlan")
        }
    }
    return context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        ?: Uri.EMPTY
}

// ─── Product picker bottom sheet ─────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProductPickerSheet(
    products: List<Product>,
    onProductSelected: (String?) -> Unit,
    onDismiss: () -> Unit
) {
    var query by remember { mutableStateOf("") }
    val filtered = remember(products, query) {
        if (query.isBlank()) products
        else products.filter {
            it.manufacturer.contains(query, ignoreCase = true) ||
                it.name.contains(query, ignoreCase = true) ||
                it.category.contains(query, ignoreCase = true)
        }
    }

    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(modifier = Modifier.padding(bottom = 32.dp)) {
            Text(
                "Välj produkt",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                placeholder = { Text("Sök tillverkare, modell...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp)
            )
            LazyColumn(modifier = Modifier.heightIn(max = 400.dp)) {
                item {
                    // Option to place symbol without product
                    ListItem(
                        headlineContent = { Text("Ingen produkt (hoppa över)") },
                        supportingContent = { Text("Symbolen placeras utan produktreferens") },
                        leadingContent = { Icon(Icons.Default.RemoveCircleOutline, contentDescription = null, tint = MaterialTheme.colorScheme.outline) },
                        modifier = Modifier.clickable { onProductSelected(null) }
                    )
                    HorizontalDivider()
                }
                items(filtered, key = { it.id }) { product ->
                    ListItem(
                        headlineContent = { Text("${product.manufacturer} ${product.name}") },
                        supportingContent = {
                            if (product.powerStandbyMa > 0f || product.powerAlarmMa > 0f) {
                                Text(buildString {
                                    if (product.powerStandbyMa > 0f) append("Vila: ${product.powerStandbyMa}mA")
                                    if (product.powerStandbyMa > 0f && product.powerAlarmMa > 0f) append("  |  ")
                                    if (product.powerAlarmMa > 0f) append("Larm: ${product.powerAlarmMa}mA")
                                })
                            } else {
                                Text(product.category)
                            }
                        },
                        leadingContent = {
                            Icon(
                                Icons.Default.Inventory2,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        },
                        modifier = Modifier.clickable { onProductSelected(product.id) }
                    )
                }
            }
        }
    }
}
