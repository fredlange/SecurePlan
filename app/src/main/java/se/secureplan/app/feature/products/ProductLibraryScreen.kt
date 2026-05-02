package se.secureplan.app.feature.products

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import se.secureplan.app.core.domain.model.Product

private data class CategoryFilter(val label: String, val key: String?)

private val CATEGORY_FILTERS = listOf(
    CategoryFilter("Alla",        null),
    CategoryFilter("Inbrott",     "INTRUSION"),
    CategoryFilter("Brand",       "FIRE"),
    CategoryFilter("Passage",     "ACCESS"),
    CategoryFilter("CCTV",        "CCTV"),
    CategoryFilter("Porttelefon", "INTERCOM"),
    CategoryFilter("Dörr",        "DOOR"),
    CategoryFilter("Övrigt",      "OTHER")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductLibraryScreen(
    onProductClick: (String) -> Unit,
    onAddProduct: () -> Unit,
    onBack: () -> Unit,
    viewModel: ProductsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var showDeleteDialog by remember { mutableStateOf<Product?>(null) }

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = { Text("Produktbibliotek", fontWeight = FontWeight.Bold) },
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
                // Search field
                OutlinedTextField(
                    value = uiState.searchQuery,
                    onValueChange = viewModel::setSearchQuery,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    placeholder = { Text("Sök produkt, tillverkare...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    trailingIcon = {
                        if (uiState.searchQuery.isNotEmpty()) {
                            IconButton(onClick = { viewModel.setSearchQuery("") }) {
                                Icon(Icons.Default.Clear, contentDescription = "Rensa")
                            }
                        }
                    },
                    singleLine = true,
                    shape = MaterialTheme.shapes.medium
                )
                // Category chips
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(CATEGORY_FILTERS) { filter ->
                        FilterChip(
                            selected = uiState.selectedCategory == filter.key,
                            onClick = {
                                viewModel.setCategory(
                                    if (uiState.selectedCategory == filter.key) null else filter.key
                                )
                            },
                            label = { Text(filter.label) }
                        )
                    }
                }
                HorizontalDivider()
            }
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onAddProduct,
                icon = { Icon(Icons.Default.Add, contentDescription = null) },
                text = { Text("Lägg till produkt") }
            )
        }
    ) { padding ->
        if (uiState.isLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (uiState.products.isEmpty()) {
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.Inventory2, contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = MaterialTheme.colorScheme.outline)
                    Spacer(Modifier.height(16.dp))
                    Text("Inga produkter hittades",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.outline)
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(
                    start = 16.dp, end = 16.dp, top = 8.dp, bottom = 88.dp
                ),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    Text(
                        "${uiState.products.size} produkter",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.outline,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
                items(uiState.products, key = { it.id }) { product ->
                    ProductCard(
                        product = product,
                        onClick = { onProductClick(product.id) },
                        onLongClick = if (product.isCustom) {
                            { showDeleteDialog = product }
                        } else null
                    )
                }
            }
        }
    }

    // Delete confirmation dialog
    showDeleteDialog?.let { product ->
        AlertDialog(
            onDismissRequest = { showDeleteDialog = null },
            title = { Text("Ta bort produkt?") },
            text = { Text("\"${product.name}\" kommer att tas bort permanent.") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.deleteProduct(product.id)
                    showDeleteDialog = null
                }) { Text("Ta bort", color = MaterialTheme.colorScheme.error) }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = null }) { Text("Avbryt") }
            }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ProductCard(
    product: Product,
    onClick: () -> Unit,
    onLongClick: (() -> Unit)?
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(onClick = onClick, onLongClick = onLongClick)
            .animateContentSize()
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Category icon
            Surface(
                color = categoryColor(product.category).copy(alpha = 0.15f),
                shape = MaterialTheme.shapes.small,
                modifier = Modifier.size(44.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = categoryIcon(product.category),
                        contentDescription = null,
                        tint = categoryColor(product.category),
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            // Main content
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        product.name,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f, fill = false)
                    )
                    if (product.isCustom) {
                        Surface(
                            color = MaterialTheme.colorScheme.tertiaryContainer,
                            shape = MaterialTheme.shapes.extraSmall
                        ) {
                            Text(
                                "Egen",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onTertiaryContainer,
                                modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                            )
                        }
                    }
                }
                Text(
                    "${product.manufacturer} · ${product.articleNumber}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(4.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    // Category chip
                    Surface(
                        color = categoryColor(product.category).copy(alpha = 0.12f),
                        shape = MaterialTheme.shapes.extraSmall
                    ) {
                        Text(
                            categoryLabel(product.category),
                            style = MaterialTheme.typography.labelSmall,
                            color = categoryColor(product.category),
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                    // Electrical spec chip
                    val specLabel = when {
                        product.powerWatt > 0f ->
                            "${product.powerWatt}W PoE"
                        product.powerStandbyMa > 0f || product.powerAlarmMa > 0f ->
                            "Vilo: ${product.powerStandbyMa.toInt()}mA  Larm: ${product.powerAlarmMa.toInt()}mA"
                        else -> null
                    }
                    if (specLabel != null) {
                        Surface(
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            shape = MaterialTheme.shapes.extraSmall
                        ) {
                            Text(
                                specLabel,
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                }
            }

            Icon(
                Icons.Default.ChevronRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.outline,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

internal fun categoryColor(category: String): Color = when (category) {
    "INTRUSION" -> Color(0xFF1565C0)
    "FIRE"      -> Color(0xFFB71C1C)
    "ACCESS"    -> Color(0xFF1B5E20)
    "CCTV"      -> Color(0xFF4A148C)
    "INTERCOM"  -> Color(0xFFE65100)
    "DOOR"      -> Color(0xFF4E342E)
    else        -> Color(0xFF546E7A)
}

internal fun categoryLabel(category: String): String = when (category) {
    "INTRUSION" -> "Inbrott"
    "FIRE"      -> "Brand"
    "ACCESS"    -> "Passage"
    "CCTV"      -> "CCTV"
    "INTERCOM"  -> "Porttelefon"
    "DOOR"      -> "Dörr"
    else        -> "Övrigt"
}

internal fun categoryIcon(category: String) = when (category) {
    "INTRUSION" -> Icons.Default.Security
    "FIRE"      -> Icons.Default.LocalFireDepartment
    "ACCESS"    -> Icons.Default.Badge
    "CCTV"      -> Icons.Default.Videocam
    "INTERCOM"  -> Icons.Default.Doorbell
    "DOOR"      -> Icons.Default.MeetingRoom
    else        -> Icons.Default.DeviceHub
}
