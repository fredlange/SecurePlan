package se.secureplan.app.feature.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import se.secureplan.app.core.domain.model.Product
import se.secureplan.app.core.domain.repository.ProductRepository
import javax.inject.Inject

data class ProductsUiState(
    val products: List<Product> = emptyList(),
    val searchQuery: String = "",
    val selectedCategory: String? = null,
    val isLoading: Boolean = true
)

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _searchQuery      = MutableStateFlow("")
    private val _selectedCategory = MutableStateFlow<String?>(null)

    val uiState: StateFlow<ProductsUiState> = combine(
        productRepository.getAllProducts(),
        _searchQuery,
        _selectedCategory
    ) { allProducts, query, category ->
        val filtered = allProducts.filter { product ->
            val matchesQuery = query.isBlank() ||
                product.name.contains(query, ignoreCase = true) ||
                product.manufacturer.contains(query, ignoreCase = true) ||
                product.articleNumber.contains(query, ignoreCase = true)
            val matchesCategory = category == null || product.category == category
            matchesQuery && matchesCategory
        }
        ProductsUiState(
            products        = filtered,
            searchQuery     = query,
            selectedCategory = category,
            isLoading       = false
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ProductsUiState()
    )

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setCategory(category: String?) {
        _selectedCategory.value = category
    }

    fun saveProduct(product: Product) {
        viewModelScope.launch { productRepository.saveProduct(product) }
    }

    fun deleteProduct(id: String) {
        viewModelScope.launch { productRepository.deleteProduct(id) }
    }
}
