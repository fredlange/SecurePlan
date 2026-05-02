package se.secureplan.app.feature.products

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    productId: String,
    onEdit: (String) -> Unit,
    onBack: () -> Unit,
    viewModel: ProductsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val product = uiState.products.find { it.id == productId }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(product?.name ?: "Produktdetaljer", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Tillbaka",
                            tint = MaterialTheme.colorScheme.onPrimary)
                    }
                },
                actions = {
                    if (product?.isCustom == true) {
                        IconButton(onClick = { onEdit(productId) }) {
                            Icon(Icons.Default.Edit, contentDescription = "Redigera",
                                tint = MaterialTheme.colorScheme.onPrimary)
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        if (product == null) {
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
            return@Scaffold
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // ── Header card ──────────────────────────────────────────────────
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Surface(
                            color = categoryColor(product.category).copy(alpha = 0.15f),
                            shape = MaterialTheme.shapes.medium,
                            modifier = Modifier.size(56.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = categoryIcon(product.category),
                                    contentDescription = null,
                                    tint = categoryColor(product.category),
                                    modifier = Modifier.size(32.dp)
                                )
                            }
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            Text(product.name,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold)
                            Text(product.manufacturer,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text(product.articleNumber,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.outline)
                        }
                    }
                    Surface(
                        color = categoryColor(product.category).copy(alpha = 0.12f),
                        shape = MaterialTheme.shapes.small
                    ) {
                        Text(
                            categoryLabel(product.category),
                            style = MaterialTheme.typography.labelMedium,
                            color = categoryColor(product.category),
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }
                }
            }

            // ── Elektriska specifikationer ────────────────────────────────────
            DetailSection(title = "Elektriska specifikationer", icon = Icons.Default.ElectricBolt) {
                if (product.powerWatt > 0f) {
                    DetailRow("Effektförbrukning", "${product.powerWatt} W")
                    DetailRow("Spänning", "${product.voltageV} V (PoE)")
                } else {
                    if (product.powerStandbyMa > 0f)
                        DetailRow("Vilström", "${product.powerStandbyMa} mA")
                    if (product.powerAlarmMa > 0f)
                        DetailRow("Larmström", "${product.powerAlarmMa} mA")
                    DetailRow("Spänning", "${product.voltageV} V")
                }
            }

            // ── Fysiska mått ─────────────────────────────────────────────────
            if (product.widthMm > 0f || product.heightMm > 0f || product.weightG > 0f) {
                DetailSection(title = "Fysiska mått", icon = Icons.Default.Straighten) {
                    if (product.widthMm > 0f || product.heightMm > 0f || product.depthMm > 0f) {
                        DetailRow("Mått (B×H×D)",
                            "${product.widthMm.toInt()} × ${product.heightMm.toInt()} × ${product.depthMm.toInt()} mm")
                    }
                    if (product.weightG > 0f)
                        DetailRow("Vikt", "${product.weightG.toInt()} g")
                }
            }

            // ── Certifieringar ───────────────────────────────────────────────
            if (product.certifications.isNotBlank()) {
                DetailSection(title = "Certifieringar", icon = Icons.Default.VerifiedUser) {
                    product.certifications.split(",").map { it.trim() }.forEach { cert ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.padding(vertical = 2.dp)
                        ) {
                            Icon(Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(16.dp))
                            Text(cert, style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }

            // ── Övrigt ───────────────────────────────────────────────────────
            DetailSection(title = "Övrigt", icon = Icons.Default.Info) {
                if (product.description.isNotBlank())
                    DetailRow("Beskrivning", product.description)
                if (product.price > 0.0)
                    DetailRow("Pris", "%.2f %s".format(product.price, product.currency))
            }

            Spacer(Modifier.height(16.dp))
        }
    }
}

@Composable
private fun DetailSection(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(bottom = 12.dp)
            ) {
                Icon(icon, contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(18.dp))
                Text(title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary)
            }
            HorizontalDivider(modifier = Modifier.padding(bottom = 8.dp))
            content()
        }
    }
}

@Composable
private fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Text(
            label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.weight(0.45f)
        )
        Text(
            value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(0.55f)
        )
    }
}
