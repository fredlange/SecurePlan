package se.secureplan.app.feature.export

import android.content.Context
import android.os.Environment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

data class ExportUiState(
    val isLoading: Boolean = false,
    val lastExportedFile: File? = null,
    val error: String? = null,
    val successMessage: String? = null
)

@HiltViewModel
class ExportViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val exportManager: ExportManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(ExportUiState())
    val uiState: StateFlow<ExportUiState> = _uiState.asStateFlow()

    fun exportPdf(projectId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val bundle = exportManager.buildBundle(projectId)
                val dir = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS) ?: context.filesDir
                val file = File(dir, "${bundle.project.name.sanitize()}_rapport.pdf")
                exportManager.exportPdf(bundle, file)
                _uiState.update { it.copy(isLoading = false, lastExportedFile = file, successMessage = "PDF exporterad!") }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }

    fun exportExcel(projectId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val bundle = exportManager.buildBundle(projectId)
                val dir = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS) ?: context.filesDir
                val file = File(dir, "${bundle.project.name.sanitize()}_strombudget.xlsx")
                exportManager.exportExcel(bundle, file)
                _uiState.update { it.copy(isLoading = false, lastExportedFile = file, successMessage = "Excel exporterad!") }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }

    fun exportZip(projectId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val bundle = exportManager.buildBundle(projectId)
                val dir = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS) ?: context.filesDir
                val zipFile = exportManager.exportZip(bundle, dir!!)
                _uiState.update { it.copy(isLoading = false, lastExportedFile = zipFile, successMessage = "ZIP exporterad!") }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }

    fun exportSecurePlan(projectId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val bundle = exportManager.buildBundle(projectId)
                val dir = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS) ?: context.filesDir
                val file = File(dir, "${bundle.project.name.sanitize()}.secureplan")
                exportManager.exportSecurePlan(bundle, file)
                _uiState.update { it.copy(isLoading = false, lastExportedFile = file, successMessage = ".secureplan exporterad!") }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }

    fun clearMessage() {
        _uiState.update { it.copy(successMessage = null, error = null) }
    }

    private fun String.sanitize() = replace("[^a-zA-Z0-9åäöÅÄÖ_-]".toRegex(), "_")
}
