package se.secureplan.app.feature.drawing

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import android.os.ParcelFileDescriptor
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import se.secureplan.app.core.domain.model.*
import se.secureplan.app.core.domain.repository.*
import java.io.File
import java.util.UUID
import javax.inject.Inject

enum class DrawingTool { SELECT, PLACE_SYMBOL, DRAW_CABLE, DRAW_ZONE, ERASE, PLACE_PHOTO }

sealed class DrawableLayer {
    object Background : DrawableLayer()
    data class SystemLayer(val type: LayerType) : DrawableLayer()
}

private val defaultLayerOrder: List<DrawableLayer> = listOf(
    DrawableLayer.Background,
    DrawableLayer.SystemLayer(LayerType.STRUCTURE),
    DrawableLayer.SystemLayer(LayerType.POWER),
    DrawableLayer.SystemLayer(LayerType.DATA),
    DrawableLayer.SystemLayer(LayerType.DETECTION),
    DrawableLayer.SystemLayer(LayerType.CONTROL)
)

data class DrawingUiState(
    val drawing: Drawing? = null,
    val placements: List<SymbolPlacement> = emptyList(),
    val cables: List<CablePath> = emptyList(),
    val zones: List<Zone> = emptyList(),
    val symbols: List<Symbol> = emptyList(),
    val activeTool: DrawingTool = DrawingTool.SELECT,
    val selectedSymbolId: String? = null,
    val selectedLayerType: LayerType = LayerType.DETECTION,
    val visibleLayers: Set<LayerType> = LayerType.values().toSet(),
    val scale: Float = 1.0f,
    val offsetX: Float = 0f,
    val offsetY: Float = 0f,
    val isLoading: Boolean = true,
    val cableInProgress: List<NormPoint> = emptyList(),
    val zoneInProgress: List<NormPoint> = emptyList(),
    val showSymbolPicker: Boolean = false,
    val showLayerPanel: Boolean = false,
    // Background image state
    val backgroundBitmap: ImageBitmap? = null,
    val showBackgroundTypePicker: Boolean = false,
    val pdfPageCount: Int = 0,
    val showPdfPagePicker: Boolean = false,
    val pendingPdfUri: Uri? = null,
    // Layer ordering & background visibility
    val isBackgroundVisible: Boolean = true,
    val layerOrder: List<DrawableLayer> = defaultLayerOrder,
    // Photo markers placed on drawing from site photos
    val photoMarkers: List<GeoPhoto> = emptyList(),
    val photoThumbnails: Map<String, ImageBitmap> = emptyMap(),
    val showPhotoMarkerPicker: Boolean = false,
    val pendingPhotoPosition: Pair<Float, Float>? = null,
    // Product library (loaded once for product picker)
    val products: List<Product> = emptyList(),
    // Product picker — shown after symbol selection (null=closed, ""=new placement, else=placementId to rebind)
    val showProductPicker: Boolean = false,
    val productPickerPlacementId: String? = null,   // null → new; non-null → rebind existing
    val selectedProductId: String? = null,
    // Placement context menu (long-press on existing symbol)
    val showPlacementMenu: Boolean = false,
    val menuPlacementId: String? = null
)

@HiltViewModel
class DrawingViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val drawingRepository: DrawingRepository,
    private val placementRepository: SymbolPlacementRepository,
    private val cableRepository: CablePathRepository,
    private val zoneRepository: ZoneRepository,
    private val symbolRepository: SymbolRepository,
    private val geoPhotoRepository: GeoPhotoRepository,
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DrawingUiState())
    val uiState: StateFlow<DrawingUiState> = _uiState.asStateFlow()

    private var currentDrawingId: String? = null
    private var lastLoadedBackgroundUri: String? = null
    private val loadedThumbnailIds = mutableSetOf<String>()

    fun loadDrawing(drawingId: String) {
        if (currentDrawingId == drawingId) return
        currentDrawingId = drawingId
        viewModelScope.launch {
            combine(
                drawingRepository.getDrawingById(drawingId),
                placementRepository.getPlacementsForDrawing(drawingId),
                cableRepository.getCablePathsForDrawing(drawingId),
                zoneRepository.getZonesForDrawing(drawingId),
                symbolRepository.getAllSymbols()
            ) { drawing, placements, cables, zones, symbols ->
                DrawingUiState(
                    drawing = drawing,
                    placements = placements,
                    cables = cables,
                    zones = zones,
                    symbols = symbols,
                    isLoading = false
                )
            }.collect { newState ->
                // Preserve transient UI state (background bitmap, dialogs, tool selection)
                _uiState.update { current ->
                    newState.copy(
                        activeTool = current.activeTool,
                        selectedSymbolId = current.selectedSymbolId,
                        selectedLayerType = current.selectedLayerType,
                        visibleLayers = current.visibleLayers,
                        scale = current.scale,
                        offsetX = current.offsetX,
                        offsetY = current.offsetY,
                        cableInProgress = current.cableInProgress,
                        zoneInProgress = current.zoneInProgress,
                        showSymbolPicker = current.showSymbolPicker,
                        showLayerPanel = current.showLayerPanel,
                        backgroundBitmap = current.backgroundBitmap,
                        showBackgroundTypePicker = current.showBackgroundTypePicker,
                        pdfPageCount = current.pdfPageCount,
                        showPdfPagePicker = current.showPdfPagePicker,
                        pendingPdfUri = current.pendingPdfUri,
                        isBackgroundVisible = current.isBackgroundVisible,
                        layerOrder = current.layerOrder,
                        photoMarkers = current.photoMarkers,
                        photoThumbnails = current.photoThumbnails,
                        showPhotoMarkerPicker = current.showPhotoMarkerPicker,
                        pendingPhotoPosition = current.pendingPhotoPosition,
                        products = current.products,
                        showProductPicker = current.showProductPicker,
                        productPickerPlacementId = current.productPickerPlacementId,
                        selectedProductId = current.selectedProductId,
                        showPlacementMenu = current.showPlacementMenu,
                        menuPlacementId = current.menuPlacementId
                    )
                }
                newState.drawing?.let { loadBackgroundBitmapIfNeeded(it) }
            }
        }
        // Separate subscription for photo markers (combine only supports 5 flows)
        viewModelScope.launch {
            geoPhotoRepository.getPhotosForDrawing(drawingId).collect { photos ->
                _uiState.update { it.copy(photoMarkers = photos) }
                loadPhotoThumbnailsIfNeeded(photos)
            }
        }
        // Separate subscription for product library
        viewModelScope.launch {
            productRepository.getAllProducts().collect { products ->
                _uiState.update { it.copy(products = products) }
            }
        }
    }

    // ─── Background image ─────────────────────────────────────────────────

    fun showBackgroundPicker() = _uiState.update { it.copy(showBackgroundTypePicker = true) }
    fun hideBackgroundPicker() = _uiState.update { it.copy(showBackgroundTypePicker = false) }
    fun hidePdfPagePicker()    = _uiState.update { it.copy(showPdfPagePicker = false, pendingPdfUri = null) }

    fun setBackgroundImage(uri: Uri) {
        val drawingId = currentDrawingId ?: return
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val destFile = backgroundFile(drawingId, "jpg")
                context.contentResolver.openInputStream(uri)?.use { input ->
                    destFile.outputStream().use { output -> input.copyTo(output) }
                }
                val bitmap = BitmapFactory.decodeFile(destFile.absolutePath)?.asImageBitmap()
                persistAndShowBackground(destFile.absolutePath, 0, bitmap)
            } catch (_: Exception) { }
        }
    }

    fun setBackgroundPdf(uri: Uri) {
        val drawingId = currentDrawingId ?: return
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val destFile = backgroundFile(drawingId, "pdf")
                context.contentResolver.openInputStream(uri)?.use { input ->
                    destFile.outputStream().use { output -> input.copyTo(output) }
                }
                val pfd = ParcelFileDescriptor.open(destFile, ParcelFileDescriptor.MODE_READ_ONLY)
                val pageCount = PdfRenderer(pfd).also { it.close() }.let {
                    PdfRenderer(ParcelFileDescriptor.open(destFile, ParcelFileDescriptor.MODE_READ_ONLY)).use { r -> r.pageCount }
                }
                pfd.close()
                if (pageCount == 1) {
                    renderAndSavePdfPage(destFile, 0)
                } else {
                    withContext(Dispatchers.Main) {
                        _uiState.update { it.copy(
                            showPdfPagePicker = true,
                            pdfPageCount = pageCount,
                            pendingPdfUri = Uri.fromFile(destFile)
                        ) }
                    }
                }
            } catch (_: Exception) { }
        }
    }

    fun selectPdfPage(pageIndex: Int) {
        val fileUri = _uiState.value.pendingPdfUri ?: return
        _uiState.update { it.copy(showPdfPagePicker = false, pendingPdfUri = null) }
        viewModelScope.launch(Dispatchers.IO) {
            renderAndSavePdfPage(File(fileUri.path!!), pageIndex)
        }
    }

    private suspend fun renderAndSavePdfPage(file: File, pageIndex: Int) {
        try {
            val pfd = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)
            val renderer = PdfRenderer(pfd)
            val page = renderer.openPage(pageIndex)
            // Render at 2× resolution for crisp display
            val bitmap = Bitmap.createBitmap(page.width * 2, page.height * 2, Bitmap.Config.ARGB_8888)
            bitmap.eraseColor(android.graphics.Color.WHITE)
            page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
            page.close()
            renderer.close()
            pfd.close()
            persistAndShowBackground(file.absolutePath, pageIndex, bitmap.asImageBitmap())
        } catch (_: Exception) { }
    }

    private suspend fun persistAndShowBackground(filePath: String, pageIndex: Int, bitmap: ImageBitmap?) {
        val drawing = _uiState.value.drawing ?: return
        val updated = drawing.copy(
            backgroundUri = filePath,
            backgroundPageIndex = pageIndex,
            updatedAt = System.currentTimeMillis()
        )
        drawingRepository.saveDrawing(updated)
        lastLoadedBackgroundUri = filePath
        withContext(Dispatchers.Main) {
            _uiState.update { it.copy(backgroundBitmap = bitmap) }
        }
    }

    private fun loadBackgroundBitmapIfNeeded(drawing: Drawing) {
        val uri = drawing.backgroundUri ?: return
        if (uri == lastLoadedBackgroundUri) return
        lastLoadedBackgroundUri = uri
        viewModelScope.launch(Dispatchers.IO) {
            val bitmap: ImageBitmap? = when {
                uri.endsWith(".pdf") -> renderPdfBitmap(File(uri), drawing.backgroundPageIndex)
                else -> runCatching { BitmapFactory.decodeFile(uri)?.asImageBitmap() }.getOrNull()
            }
            withContext(Dispatchers.Main) {
                _uiState.update { it.copy(backgroundBitmap = bitmap) }
            }
        }
    }

    private fun renderPdfBitmap(file: File, pageIndex: Int): ImageBitmap? = runCatching {
        val pfd = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)
        val renderer = PdfRenderer(pfd)
        val safeIndex = pageIndex.coerceIn(0, renderer.pageCount - 1)
        val page = renderer.openPage(safeIndex)
        val bitmap = Bitmap.createBitmap(page.width * 2, page.height * 2, Bitmap.Config.ARGB_8888)
        bitmap.eraseColor(android.graphics.Color.WHITE)
        page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
        page.close(); renderer.close(); pfd.close()
        bitmap.asImageBitmap()
    }.getOrNull()

    private fun backgroundFile(drawingId: String, ext: String): File {
        val dir = File(context.filesDir, "backgrounds").also { it.mkdirs() }
        return File(dir, "$drawingId.$ext")
    }

    // ─── Tool selection ───────────────────────────────────────────────────
    fun selectTool(tool: DrawingTool) {
        _uiState.update { it.copy(activeTool = tool, cableInProgress = emptyList(), zoneInProgress = emptyList()) }
    }

    fun selectSymbol(symbolId: String) {
        _uiState.update { it.copy(
            selectedSymbolId = symbolId,
            showSymbolPicker = false,
            showProductPicker = true,       // prompt to pick a product after symbol
            productPickerPlacementId = null // null = for new placement
        ) }
    }

    fun selectProductForPicker(productId: String?) {
        val mode = _uiState.value.productPickerPlacementId
        if (mode != null) {
            // Rebind product on an existing placement
            bindProductToPlacement(mode, productId)
        } else {
            // Store for the next placement
            _uiState.update { it.copy(selectedProductId = productId, showProductPicker = false) }
        }
    }

    fun hideProductPicker() {
        _uiState.update { it.copy(showProductPicker = false, productPickerPlacementId = null) }
    }

    fun showPlacementContextMenu(placementId: String) {
        _uiState.update { it.copy(showPlacementMenu = true, menuPlacementId = placementId) }
    }

    fun hidePlacementContextMenu() {
        _uiState.update { it.copy(showPlacementMenu = false, menuPlacementId = null) }
    }

    fun openProductPickerForExisting(placementId: String) {
        hidePlacementContextMenu()
        _uiState.update { it.copy(showProductPicker = true, productPickerPlacementId = placementId) }
    }

    fun bindProductToPlacement(placementId: String, productId: String?) {
        val placement = _uiState.value.placements.find { it.id == placementId } ?: return
        viewModelScope.launch { placementRepository.savePlacement(placement.copy(productId = productId)) }
        _uiState.update { it.copy(showProductPicker = false, productPickerPlacementId = null) }
    }

    fun selectLayer(layer: LayerType) {
        _uiState.update { it.copy(selectedLayerType = layer) }
    }

    fun toggleLayerVisibility(layer: LayerType) {
        _uiState.update { state ->
            val visible = state.visibleLayers.toMutableSet()
            if (layer in visible) visible.remove(layer) else visible.add(layer)
            state.copy(visibleLayers = visible)
        }
    }

    fun toggleBackgroundVisibility() {
        _uiState.update { it.copy(isBackgroundVisible = !it.isBackgroundVisible) }
    }

    /** Move layer one step higher in render order (rendered on top of more content). */
    fun moveLayerUp(layer: DrawableLayer) {
        _uiState.update { state ->
            val list = state.layerOrder.toMutableList()
            val idx = list.indexOf(layer)
            if (idx in 0 until list.size - 1) {
                val tmp = list[idx]; list[idx] = list[idx + 1]; list[idx + 1] = tmp
            }
            state.copy(layerOrder = list)
        }
    }

    /** Move layer one step lower in render order (rendered behind more content). */
    fun moveLayerDown(layer: DrawableLayer) {
        _uiState.update { state ->
            val list = state.layerOrder.toMutableList()
            val idx = list.indexOf(layer)
            if (idx > 0) {
                val tmp = list[idx]; list[idx] = list[idx - 1]; list[idx - 1] = tmp
            }
            state.copy(layerOrder = list)
        }
    }

    // ─── Gesture state ────────────────────────────────────────────────────
    fun updateTransform(scale: Float, offsetX: Float, offsetY: Float) {
        _uiState.update { it.copy(scale = scale, offsetX = offsetX, offsetY = offsetY) }
    }

    // ─── Canvas tap handler ───────────────────────────────────────────────
    fun onCanvasTap(xNorm: Float, yNorm: Float) {
        when (_uiState.value.activeTool) {
            DrawingTool.PLACE_SYMBOL -> placeSymbol(xNorm, yNorm)
            DrawingTool.DRAW_CABLE   -> addCablePoint(xNorm, yNorm)
            DrawingTool.DRAW_ZONE    -> addZonePoint(xNorm, yNorm)
            DrawingTool.ERASE        -> { /* handled by individual item long-press */ }
            DrawingTool.SELECT       -> { /* TODO: hit-test and select item */ }
            DrawingTool.PLACE_PHOTO  -> {
                _uiState.update { it.copy(
                    pendingPhotoPosition = Pair(xNorm, yNorm),
                    showPhotoMarkerPicker = true
                ) }
            }
        }
    }

    private fun placeSymbol(xNorm: Float, yNorm: Float) {
        val symbolId = _uiState.value.selectedSymbolId ?: return
        val drawingId = currentDrawingId ?: return
        val productId = _uiState.value.selectedProductId
        viewModelScope.launch {
            val placement = SymbolPlacement(
                id = UUID.randomUUID().toString(),
                drawingId = drawingId,
                symbolId = symbolId,
                productId = productId,
                xNorm = xNorm,
                yNorm = yNorm,
                rotation = 0f,
                label = "",
                notes = "",
                layerType = _uiState.value.selectedLayerType,
                isVisible = true,
                createdAt = System.currentTimeMillis()
            )
            placementRepository.savePlacement(placement)
        }
    }

    private fun addCablePoint(xNorm: Float, yNorm: Float) {
        _uiState.update { it.copy(cableInProgress = it.cableInProgress + NormPoint(xNorm, yNorm)) }
    }

    fun finishCable() {
        val points = _uiState.value.cableInProgress
        if (points.size < 2) { _uiState.update { it.copy(cableInProgress = emptyList()) }; return }
        val drawingId = currentDrawingId ?: return
        viewModelScope.launch {
            val cable = CablePath(
                id = UUID.randomUUID().toString(),
                drawingId = drawingId,
                points = points,
                cableType = "UTP Cat6",
                colorHex = "#FF5722",
                strokeWidth = 4f,
                label = "",
                notes = "",
                layerType = _uiState.value.selectedLayerType,
                isVisible = true,
                createdAt = System.currentTimeMillis()
            )
            cableRepository.saveCablePath(cable)
            _uiState.update { it.copy(cableInProgress = emptyList()) }
        }
    }

    private fun addZonePoint(xNorm: Float, yNorm: Float) {
        _uiState.update { it.copy(zoneInProgress = it.zoneInProgress + NormPoint(xNorm, yNorm)) }
    }

    fun finishZone() {
        val points = _uiState.value.zoneInProgress
        if (points.size < 3) { _uiState.update { it.copy(zoneInProgress = emptyList()) }; return }
        val drawingId = currentDrawingId ?: return
        viewModelScope.launch {
            val zoneNum = zoneRepository.getNextZoneNumber(drawingId)
            val zone = Zone(
                id = UUID.randomUUID().toString(),
                drawingId = drawingId,
                name = "Zone $zoneNum",
                zoneNumber = zoneNum,
                polygon = points,
                fillColorHex = "#3F51B5",
                fillAlpha = 0.25f,
                strokeColorHex = "#1A237E",
                notes = "",
                isVisible = true,
                createdAt = System.currentTimeMillis()
            )
            zoneRepository.saveZone(zone)
            _uiState.update { it.copy(zoneInProgress = emptyList()) }
        }
    }

    fun deletePlacement(id: String) {
        viewModelScope.launch { placementRepository.deletePlacement(id) }
    }

    fun deleteCable(id: String) {
        viewModelScope.launch { cableRepository.deleteCablePath(id) }
    }

    fun deleteZone(id: String) {
        viewModelScope.launch { zoneRepository.deleteZone(id) }
    }

    fun showSymbolPicker() = _uiState.update { it.copy(showSymbolPicker = true) }
    fun hideSymbolPicker() = _uiState.update { it.copy(showSymbolPicker = false) }
    fun toggleLayerPanel() = _uiState.update { it.copy(showLayerPanel = !it.showLayerPanel) }

    // ─── Photo markers ────────────────────────────────────────────────────

    /** Hides the picker dialog but keeps pendingPhotoPosition so the launcher can use it. */
    fun hidePhotoMarkerPicker() = _uiState.update { it.copy(showPhotoMarkerPicker = false) }

    /** Cancels the whole photo-placement flow (e.g. user pressed Avbryt). */
    fun cancelPhotoMarkerPicker() = _uiState.update {
        it.copy(showPhotoMarkerPicker = false, pendingPhotoPosition = null)
    }

    fun placePhotoMarker(uri: Uri) {
        val (xNorm, yNorm) = _uiState.value.pendingPhotoPosition ?: return
        val drawingId = currentDrawingId ?: return
        val projectId = _uiState.value.drawing?.projectId ?: return
        _uiState.update { it.copy(pendingPhotoPosition = null) }
        viewModelScope.launch {
            // Copy the photo to internal storage so the content URI stays valid permanently
            val photoId = UUID.randomUUID().toString()
            val destFile = File(File(context.filesDir, "photos").also { it.mkdirs() }, "$photoId.jpg")
            val storedPath = withContext(Dispatchers.IO) {
                runCatching {
                    context.contentResolver.openInputStream(uri)?.use { input ->
                        destFile.outputStream().use { out -> input.copyTo(out) }
                    }
                    destFile.absolutePath
                }.getOrNull() ?: uri.toString()
            }
            val photo = GeoPhoto(
                id        = photoId,
                projectId = projectId,
                drawingId = drawingId,
                photoUri  = storedPath,
                latitude  = null,
                longitude = null,
                caption   = "",
                takenAt   = System.currentTimeMillis(),
                xNorm     = xNorm,
                yNorm     = yNorm
            )
            geoPhotoRepository.savePhoto(photo)
        }
    }

    fun deletePhotoMarker(id: String) {
        viewModelScope.launch { geoPhotoRepository.deletePhoto(id) }
    }

    private fun loadPhotoThumbnailsIfNeeded(photos: List<GeoPhoto>) {
        val newPhotos = photos.filter { it.id !in loadedThumbnailIds && it.photoUri.isNotBlank() }
        if (newPhotos.isEmpty()) return
        viewModelScope.launch(Dispatchers.IO) {
            val newThumbnails = mutableMapOf<String, ImageBitmap>()
            for (photo in newPhotos) {
                val bitmap = runCatching {
                    val options = BitmapFactory.Options().apply { inSampleSize = 4 }
                    val uri = Uri.parse(photo.photoUri)
                    if (photo.photoUri.startsWith("/")) {
                        // Absolute file path
                        BitmapFactory.decodeFile(photo.photoUri, options)?.asImageBitmap()
                    } else {
                        // Content URI
                        context.contentResolver.openInputStream(uri)?.use { stream ->
                            BitmapFactory.decodeStream(stream, null, options)
                        }?.asImageBitmap()
                    }
                }.getOrNull()
                if (bitmap != null) {
                    newThumbnails[photo.id] = bitmap
                    loadedThumbnailIds.add(photo.id)
                }
            }
            if (newThumbnails.isNotEmpty()) {
                withContext(Dispatchers.Main) {
                    _uiState.update { it.copy(photoThumbnails = it.photoThumbnails + newThumbnails) }
                }
            }
        }
    }
}
