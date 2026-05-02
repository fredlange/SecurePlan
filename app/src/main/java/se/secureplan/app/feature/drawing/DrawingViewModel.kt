package se.secureplan.app.feature.drawing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import se.secureplan.app.core.domain.model.*
import se.secureplan.app.core.domain.repository.*
import java.util.UUID
import javax.inject.Inject

enum class DrawingTool { SELECT, PLACE_SYMBOL, DRAW_CABLE, DRAW_ZONE, ERASE }

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
    val showLayerPanel: Boolean = false
)

@HiltViewModel
class DrawingViewModel @Inject constructor(
    private val drawingRepository: DrawingRepository,
    private val placementRepository: SymbolPlacementRepository,
    private val cableRepository: CablePathRepository,
    private val zoneRepository: ZoneRepository,
    private val symbolRepository: SymbolRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DrawingUiState())
    val uiState: StateFlow<DrawingUiState> = _uiState.asStateFlow()

    private var currentDrawingId: String? = null

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
                    isLoading = false,
                    activeTool = _uiState.value.activeTool,
                    selectedSymbolId = _uiState.value.selectedSymbolId,
                    selectedLayerType = _uiState.value.selectedLayerType,
                    visibleLayers = _uiState.value.visibleLayers,
                    scale = _uiState.value.scale,
                    offsetX = _uiState.value.offsetX,
                    offsetY = _uiState.value.offsetY
                )
            }.collect { state -> _uiState.value = state }
        }
    }

    // ─── Tool selection ───────────────────────────────────────────────────
    fun selectTool(tool: DrawingTool) {
        _uiState.update { it.copy(activeTool = tool, cableInProgress = emptyList(), zoneInProgress = emptyList()) }
    }

    fun selectSymbol(symbolId: String) {
        _uiState.update { it.copy(selectedSymbolId = symbolId, showSymbolPicker = false) }
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
        }
    }

    private fun placeSymbol(xNorm: Float, yNorm: Float) {
        val symbolId = _uiState.value.selectedSymbolId ?: return
        val drawingId = currentDrawingId ?: return
        viewModelScope.launch {
            val placement = SymbolPlacement(
                id = UUID.randomUUID().toString(),
                drawingId = drawingId,
                symbolId = symbolId,
                productId = null,
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
}
