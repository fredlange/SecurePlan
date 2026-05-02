package se.secureplan.app.feature.components

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import se.secureplan.app.core.domain.model.Product
import se.secureplan.app.core.domain.model.SymbolPlacement
import se.secureplan.app.core.domain.repository.DrawingRepository
import se.secureplan.app.core.domain.repository.ProductRepository
import se.secureplan.app.core.domain.repository.SymbolPlacementRepository
import javax.inject.Inject

data class ComponentItem(
    val symbolId: String,
    val productId: String?,
    val productName: String,
    val manufacturer: String,
    val count: Int,
    val layerType: String
)

data class CategorySection(
    val category: String,
    val label: String,
    val items: List<ComponentItem>,
    val totalCount: Int
)

data class ComponentSummaryUiState(
    val sections: List<CategorySection> = emptyList(),
    val totalComponents: Int = 0,
    val isLoading: Boolean = true
)

@HiltViewModel
class ComponentSummaryViewModel @Inject constructor(
    private val symbolPlacementRepository: SymbolPlacementRepository,
    private val productRepository: ProductRepository,
    private val drawingRepository: DrawingRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ComponentSummaryUiState())
    val uiState: StateFlow<ComponentSummaryUiState> = _uiState.asStateFlow()

    private var currentProjectId: String? = null

    fun loadProject(projectId: String) {
        if (currentProjectId == projectId) return
        currentProjectId = projectId

        viewModelScope.launch {
            combine(
                symbolPlacementRepository.getPlacementsForProject(projectId),
                productRepository.getAllProducts()
            ) { placements, products ->
                buildSummary(placements, products)
            }.collect { state ->
                _uiState.value = state
            }
        }
    }

    private fun buildSummary(
        placements: List<SymbolPlacement>,
        products: List<Product>
    ): ComponentSummaryUiState {
        val productMap: Map<String, Product> = products.associateBy { it.id }

        // Group by (layerType, productId/symbolId) → count
        data class GroupKey(val layerType: String, val productId: String?, val symbolId: String)

        val groups = placements.groupBy { placement ->
            GroupKey(
                layerType = placement.layerType.name,
                productId = placement.productId,
                symbolId  = placement.symbolId
            )
        }

        val items = groups.map { (key, group) ->
            val product = key.productId?.let { productMap[it] }
            ComponentItem(
                symbolId     = key.symbolId,
                productId    = key.productId,
                productName  = product?.name ?: (key.productId?.let { "Okänd produkt" } ?: "Symbol utan produkt"),
                manufacturer = product?.manufacturer ?: "",
                count        = group.size,
                layerType    = key.layerType
            )
        }

        // Map layerType to category label
        val categoryOrder = listOf("INTRUSION", "FIRE", "ACCESS", "CCTV", "INTERCOM", "DOOR", "OTHER")
        val categoryLabels = mapOf(
            "INTRUSION" to "Inbrottslarm",
            "FIRE"      to "Brandlarm",
            "ACCESS"    to "Passerkontroll",
            "CCTV"      to "CCTV / Kamera",
            "INTERCOM"  to "Porttelefon",
            "DOOR"      to "Dörrar",
            "OTHER"     to "Övrigt"
        )

        val sections = items
            .groupBy { it.layerType }
            .entries
            .sortedBy { (cat, _) -> categoryOrder.indexOf(cat).takeIf { it >= 0 } ?: Int.MAX_VALUE }
            .map { (cat, catItems) ->
                CategorySection(
                    category   = cat,
                    label      = categoryLabels[cat] ?: cat,
                    items      = catItems.sortedBy { it.productName },
                    totalCount = catItems.sumOf { it.count }
                )
            }

        return ComponentSummaryUiState(
            sections        = sections,
            totalComponents = placements.size,
            isLoading       = false
        )
    }
}
