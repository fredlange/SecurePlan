package se.secureplan.app.feature.calculations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import se.secureplan.app.core.domain.model.Product
import se.secureplan.app.core.domain.model.SymbolPlacement
import se.secureplan.app.core.domain.repository.CalculationRepository
import se.secureplan.app.core.domain.repository.DrawingRepository
import se.secureplan.app.core.domain.repository.ProductRepository
import se.secureplan.app.core.domain.repository.SymbolPlacementRepository
import javax.inject.Inject

data class EditableComponentLoad(
    val productId: String?,
    val name: String,
    val count: Int,
    val standbyMaInput: String,
    val alarmMaInput: String,
    val voltageV: Float,
    val isPoE: Boolean = false,
    val powerWattInput: String = "0"
)

data class CalculationsUiState(
    val componentLoads: List<EditableComponentLoad> = emptyList(),
    val cameraLoads: List<EditableComponentLoad> = emptyList(),
    val ssf130Result: Ssf130Calculator.Ssf130Result? = null,
    val ssf1060Result: Ssf1060Calculator.Ssf1060Result? = null,
    val isLoading: Boolean = true
)

@HiltViewModel
class CalculationsViewModel @Inject constructor(
    private val symbolPlacementRepository: SymbolPlacementRepository,
    private val productRepository: ProductRepository,
    private val drawingRepository: DrawingRepository,
    private val calculationRepository: CalculationRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CalculationsUiState())
    val uiState: StateFlow<CalculationsUiState> = _uiState.asStateFlow()

    private var currentProjectId: String? = null

    fun loadProject(projectId: String) {
        if (currentProjectId == projectId) return
        currentProjectId = projectId

        viewModelScope.launch {
            combine(
                symbolPlacementRepository.getPlacementsForProject(projectId),
                productRepository.getAllProducts()
            ) { placements, products ->
                buildEditableLoads(placements, products)
            }.collect { (intrusion, cctv) ->
                _uiState.update { it.copy(
                    componentLoads = intrusion,
                    cameraLoads    = cctv,
                    isLoading      = false
                ) }
            }
        }
    }

    private fun buildEditableLoads(
        placements: List<SymbolPlacement>,
        products: List<Product>
    ): Pair<List<EditableComponentLoad>, List<EditableComponentLoad>> {
        val productMap = products.associateBy { it.id }

        // Group placements by productId (or symbolId if no product linked)
        data class Key(val productId: String?, val symbolId: String)
        val grouped = placements.groupBy { Key(it.productId, it.symbolId) }

        val allLoads = grouped.map { (key, group) ->
            val product = key.productId?.let { productMap[it] }
            val isCctv = product?.category == "CCTV" ||
                group.first().layerType.name == "CCTV"

            EditableComponentLoad(
                productId       = key.productId,
                name            = product?.name ?: "Symbol (okänd produkt)",
                count           = group.size,
                standbyMaInput  = product?.powerStandbyMa?.toString() ?: "0",
                alarmMaInput    = product?.powerAlarmMa?.toString() ?: "0",
                voltageV        = product?.voltageV ?: 12f,
                isPoE           = (product?.voltageV ?: 0f) >= 24f && (product?.powerWatt ?: 0f) > 0f,
                powerWattInput  = product?.powerWatt?.toString() ?: "0"
            )
        }

        val intrusion = allLoads.filter { load ->
            val product = load.productId?.let { productMap[it] }
            product?.category != "CCTV"
        }
        val cctv = allLoads.filter { load ->
            val product = load.productId?.let { productMap[it] }
            product?.category == "CCTV"
        }

        return Pair(intrusion, cctv)
    }

    fun updateComponentStandby(index: Int, value: String) {
        val updated = _uiState.value.componentLoads.toMutableList()
        if (index < updated.size) {
            updated[index] = updated[index].copy(standbyMaInput = value)
            _uiState.update { it.copy(componentLoads = updated) }
        }
    }

    fun updateComponentAlarm(index: Int, value: String) {
        val updated = _uiState.value.componentLoads.toMutableList()
        if (index < updated.size) {
            updated[index] = updated[index].copy(alarmMaInput = value)
            _uiState.update { it.copy(componentLoads = updated) }
        }
    }

    fun updateCameraWatt(index: Int, value: String) {
        val updated = _uiState.value.cameraLoads.toMutableList()
        if (index < updated.size) {
            updated[index] = updated[index].copy(powerWattInput = value)
            _uiState.update { it.copy(cameraLoads = updated) }
        }
    }

    fun calculateSsf130() {
        val loads = _uiState.value.componentLoads.map { item ->
            Ssf130Calculator.ComponentLoad(
                name          = item.name,
                count         = item.count,
                standbyMaEach = item.standbyMaInput.toFloatOrNull() ?: 0f,
                alarmMaEach   = item.alarmMaInput.toFloatOrNull() ?: 0f,
                voltageV      = item.voltageV
            )
        }
        val result = Ssf130Calculator.calculate(loads)
        _uiState.update { it.copy(ssf130Result = result) }
    }

    fun calculateSsf1060() {
        val cameraLoads = _uiState.value.cameraLoads.map { item ->
            Ssf1060Calculator.CameraLoad(
                name          = item.name,
                count         = item.count,
                powerWattEach = item.powerWattInput.toFloatOrNull() ?: 0f,
                isPoe         = item.isPoE
            )
        }
        // The first load with "NVR" in its name is treated as the NVR
        val nvrItem = cameraLoads.find { it.name.contains("NVR", ignoreCase = true) ||
            it.name.contains("DVR", ignoreCase = true) }
        val cameras = if (nvrItem != null) cameraLoads - nvrItem else cameraLoads
        val nvr = nvrItem?.let {
            Ssf1060Calculator.NvrLoad(name = it.name, powerWatt = it.powerWattEach * it.count)
        }
        val result = Ssf1060Calculator.calculate(cameras, nvr)
        _uiState.update { it.copy(ssf1060Result = result) }
    }
}
