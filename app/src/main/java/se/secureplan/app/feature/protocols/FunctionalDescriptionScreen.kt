package se.secureplan.app.feature.protocols

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

@Composable
fun FunctionalDescriptionScreen(
    projectId: String,
    onOpenForm: (templateId: String?, instanceId: String?) -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        Icons.Default.Description,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        "Anläggningsbeskrivning",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Text(
                    "En strukturerad beskrivning av hela säkerhetssystemet. Dokumenterar installerade systemtyper, konfigurationer och godkännanden.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(Modifier.height(4.dp))
                Button(
                    onClick = { onOpenForm("functional_description_v1", null) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.Edit, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("Öppna / Redigera anläggningsbeskrivning")
                }
            }
        }

        Text(
            "Innehåll i anläggningsbeskrivningen:",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.outline
        )

        val sections = listOf(
            "Anläggningsinformation" to "Kund, fastighet, adress, installatör, datum, systemtyper",
            "Inbrottslarmsbeskrivning" to "Larmcentral, zoner, detektorer, fördröjningar",
            "CCTV-systembeskrivning" to "NVR/DVR, kameror, inspelningsdygn, fjärråtkomst",
            "Passagesystembeskrivning" to "System, dörrar, kortformat, antipassback",
            "Övrigt" to "Generella anteckningar och specialinstruktioner",
            "Godkännande" to "Datum, teknikerns och kundens underskrift"
        )
        sections.forEach { (title, desc) ->
            ListItem(
                headlineContent = {
                    Text(
                        title,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium
                    )
                },
                supportingContent = { Text(desc, style = MaterialTheme.typography.bodySmall) },
                leadingContent = {
                    Icon(
                        Icons.Default.CheckCircleOutline,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            )
        }
    }
}
