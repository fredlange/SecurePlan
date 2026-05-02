package se.secureplan.app.feature.protocols

import android.app.DatePickerDialog
import android.graphics.Bitmap
import android.util.Base64
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import java.io.ByteArrayOutputStream
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProtocolFormScreen(
    templateId: String?,
    projectId: String,
    instanceId: String?,
    onBack: () -> Unit,
    viewModel: ProtocolViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()

    LaunchedEffect(templateId, projectId, instanceId) {
        viewModel.loadOrCreateInstance(templateId, projectId, instanceId)
    }

    LaunchedEffect(uiState.isSaved) {
        if (uiState.isSaved) onBack()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(uiState.template?.name ?: "Protokoll") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Tillbaka")
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.saveAsDraft() }) {
                        Icon(Icons.Default.Save, contentDescription = "Spara")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        if (uiState.isLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(scrollState)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                uiState.fields.forEach { field ->
                    ProtocolFieldItem(
                        field = field,
                        value = uiState.fieldValues[field.id] ?: "",
                        onValueChange = { viewModel.updateFieldValue(field.id, it) }
                    )
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                Text("Signatur", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)

                OutlinedTextField(
                    value = uiState.technicianName,
                    onValueChange = { viewModel.updateTechnicianName(it) },
                    label = { Text("Teknikerns namn") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Text("Rita din signatur:", style = MaterialTheme.typography.labelLarge)
                SignaturePad(
                    onSignatureCapture = { viewModel.updateSignature(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                )

                Spacer(Modifier.height(8.dp))
                Button(
                    onClick = { viewModel.saveAsCompleted() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.CheckCircle, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("Spara & Slutför")
                }
                OutlinedButton(
                    onClick = { viewModel.saveAsDraft() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Spara utkast")
                }
                Spacer(Modifier.height(32.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProtocolFieldItem(
    field: ProtocolField,
    value: String,
    onValueChange: (String) -> Unit
) {
    val context = LocalContext.current
    when (field.type) {
        "text" -> OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(field.label + if (field.required) " *" else "") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        "number" -> OutlinedTextField(
            value = value,
            onValueChange = { if (it.all { c -> c.isDigit() || c == '.' || c == '-' }) onValueChange(it) },
            label = { Text(field.label + if (field.required) " *" else "") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        "textarea" -> OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(field.label) },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3,
            maxLines = 6
        )
        "checkbox" -> Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = value == "true",
                onCheckedChange = { onValueChange(it.toString()) }
            )
            Spacer(Modifier.width(8.dp))
            Text(field.label + if (field.required) " *" else "")
        }
        "date" -> {
            val calendar = Calendar.getInstance()
            val datePickerDialog = DatePickerDialog(
                context,
                { _, year, month, day ->
                    onValueChange(
                        "$year-${(month + 1).toString().padStart(2, '0')}-${day.toString().padStart(2, '0')}"
                    )
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            OutlinedTextField(
                value = value,
                onValueChange = {},
                label = { Text(field.label + if (field.required) " *" else "") },
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = { datePickerDialog.show() }) {
                        Icon(Icons.Default.DateRange, contentDescription = "Välj datum")
                    }
                }
            )
        }
        else -> OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(field.label) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
    }
}

@Composable
private fun SignaturePad(
    onSignatureCapture: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val paths = remember { mutableStateListOf<Pair<Offset, Offset>>() }
    var currentPath by remember { mutableStateOf<List<Offset>>(emptyList()) }
    val strokeColor = MaterialTheme.colorScheme.onSurface

    Column {
        Box(
            modifier = modifier
                .border(1.dp, MaterialTheme.colorScheme.outline, MaterialTheme.shapes.medium)
                .background(MaterialTheme.colorScheme.surface, MaterialTheme.shapes.medium)
        ) {
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = { offset -> currentPath = listOf(offset) },
                            onDrag = { change, _ ->
                                val prev = currentPath.lastOrNull() ?: change.position
                                paths.add(Pair(prev, change.position))
                                currentPath = currentPath + change.position
                            },
                            onDragEnd = {
                                val bitmap = Bitmap.createBitmap(400, 200, Bitmap.Config.ARGB_8888)
                                val canvas = android.graphics.Canvas(bitmap)
                                canvas.drawColor(android.graphics.Color.WHITE)
                                val paint = android.graphics.Paint().apply {
                                    color = android.graphics.Color.BLACK
                                    strokeWidth = 4f
                                    style = android.graphics.Paint.Style.STROKE
                                    isAntiAlias = true
                                    strokeCap = android.graphics.Paint.Cap.ROUND
                                }
                                paths.forEach { (start, end) ->
                                    canvas.drawLine(start.x, start.y, end.x, end.y, paint)
                                }
                                val baos = ByteArrayOutputStream()
                                bitmap.compress(Bitmap.CompressFormat.PNG, 90, baos)
                                val base64 = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT)
                                onSignatureCapture(base64)
                                currentPath = emptyList()
                            }
                        )
                    }
            ) {
                paths.forEach { (start, end) ->
                    drawLine(
                        color = strokeColor,
                        start = start,
                        end = end,
                        strokeWidth = 4f
                    )
                }
            }
        }
        TextButton(
            onClick = {
                paths.clear()
                currentPath = emptyList()
                onSignatureCapture("")
            }
        ) {
            Icon(Icons.Default.Clear, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(Modifier.width(4.dp))
            Text("Rensa signatur")
        }
    }
}
