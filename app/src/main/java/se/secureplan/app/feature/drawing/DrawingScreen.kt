package se.secureplan.app.feature.drawing

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.*
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
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
                    onDeleteZone = viewModel::deleteZone
                )
            }

            // Layer panel overlay
            if (uiState.showLayerPanel) {
                LayerPanel(
                    visibleLayers = uiState.visibleLayers,
                    onToggle = viewModel::toggleLayerVisibility,
                    onClose = viewModel::toggleLayerPanel,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                )
            }
        }
    }

    // Symbol picker bottom sheet
    if (uiState.showSymbolPicker) {
        SymbolPickerSheet(
            symbols = uiState.symbols,
            onSymbolSelected = viewModel::selectSymbol,
            onDismiss = viewModel::hideSymbolPicker
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
    onDeleteZone: (String) -> Unit
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
                detectTapGestures(
                    onTap = { tapOffset ->
                        // Convert screen coordinates to normalized canvas coordinates
                        val canvasW = size.width.toFloat()
                        val canvasH = size.height.toFloat()
                        val xNorm = ((tapOffset.x - offsetX) / (canvasW * scale)).coerceIn(0f, 1f)
                        val yNorm = ((tapOffset.y - offsetY) / (canvasH * scale)).coerceIn(0f, 1f)
                        onTap(xNorm, yNorm)
                    },
                    onLongPress = { /* TODO: context menu for deletion */ }
                )
            }
    ) {
        val canvasW = size.width
        val canvasH = size.height

        // Apply pan/zoom transform
        drawContext.transform.translate(offsetX, offsetY)
        drawContext.transform.scale(scale, scale)

        // Draw background grid
        drawGrid(canvasW, canvasH)

        // Draw visible zones
        if (LayerType.DETECTION in uiState.visibleLayers) {
            uiState.zones.filter { it.isVisible }.forEach { zone ->
                drawZone(zone, canvasW, canvasH)
            }
        }

        // Draw in-progress zone
        uiState.zoneInProgress.let { pts ->
            if (pts.size >= 2) {
                drawInProgressPolygon(pts, canvasW, canvasH)
            }
        }

        // Draw cables
        LayerType.values().forEach { layer ->
            if (layer in uiState.visibleLayers) {
                uiState.cables.filter { it.layerType == layer && it.isVisible }.forEach { cable ->
                    drawCablePath(cable, canvasW, canvasH)
                }
            }
        }

        // Draw in-progress cable
        uiState.cableInProgress.let { pts ->
            if (pts.size >= 2) {
                drawInProgressLine(pts, canvasW, canvasH)
            }
        }

        // Draw symbol placements
        LayerType.values().forEach { layer ->
            if (layer in uiState.visibleLayers) {
                uiState.placements.filter { it.layerType == layer && it.isVisible }.forEach { placement ->
                    drawSymbolMarker(placement, canvasW, canvasH)
                }
            }
        }
    }
}

private fun DrawScope.drawGrid(w: Float, h: Float) {
    val gridSpacing = 50f
    val gridColor = Color(0x22000000)
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
            ToolButton(Icons.Default.PanTool, "Select",
                activeTool == DrawingTool.SELECT) { onToolSelected(DrawingTool.SELECT) }
            ToolButton(Icons.Default.AddCircle, "Place Symbol",
                activeTool == DrawingTool.PLACE_SYMBOL) { onSymbolPickerOpen() }
            ToolButton(Icons.Default.Timeline, "Draw Cable",
                activeTool == DrawingTool.DRAW_CABLE) {
                if (cableInProgress) onFinishCable()
                else onToolSelected(DrawingTool.DRAW_CABLE)
            }
            ToolButton(Icons.Default.CropFree, "Draw Zone",
                activeTool == DrawingTool.DRAW_ZONE) {
                if (zoneInProgress) onFinishZone()
                else onToolSelected(DrawingTool.DRAW_ZONE)
            }
            ToolButton(Icons.Default.DeleteOutline, "Erase",
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
    onToggle: (LayerType) -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier, elevation = CardDefaults.cardElevation(8.dp)) {
        Column(modifier = Modifier.padding(12.dp).width(180.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Layers", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f))
                IconButton(onClick = onClose, modifier = Modifier.size(24.dp)) {
                    Icon(Icons.Default.Close, contentDescription = "Close", modifier = Modifier.size(16.dp))
                }
            }
            Spacer(Modifier.height(4.dp))
            LayerType.values().forEach { layer ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Checkbox(checked = layer in visibleLayers, onCheckedChange = { onToggle(layer) })
                    Text(layer.name, style = MaterialTheme.typography.bodySmall)
                }
            }
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
