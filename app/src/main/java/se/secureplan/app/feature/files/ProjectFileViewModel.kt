package se.secureplan.app.feature.files

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import se.secureplan.app.core.domain.model.ProjectFile
import se.secureplan.app.core.domain.repository.ProjectFileRepository
import java.io.File
import java.util.UUID
import javax.inject.Inject

data class ProjectFileUiState(
    val files: List<ProjectFile> = emptyList(),
    val isLoading: Boolean = true,
    val showDeleteDialog: Boolean = false,
    val fileToDelete: ProjectFile? = null,
    val showRenameDialog: Boolean = false,
    val fileToRename: ProjectFile? = null,
    val errorMessage: String? = null
)

@HiltViewModel
class ProjectFileViewModel @Inject constructor(
    private val projectFileRepository: ProjectFileRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProjectFileUiState())
    val uiState: StateFlow<ProjectFileUiState> = _uiState.asStateFlow()

    private var currentProjectId: String? = null

    fun loadFiles(projectId: String) {
        if (currentProjectId == projectId) return
        currentProjectId = projectId
        viewModelScope.launch {
            projectFileRepository.getFilesForProject(projectId)
                .collect { files ->
                    _uiState.update { it.copy(files = files, isLoading = false) }
                }
        }
    }

    fun addFile(uri: Uri) {
        val projectId = currentProjectId ?: return
        viewModelScope.launch {
            try {
                val mimeType = context.contentResolver.getType(uri) ?: "application/octet-stream"
                val displayName = queryDisplayName(uri) ?: "bestand_${System.currentTimeMillis()}"
                val ext = displayName.substringAfterLast('.', "")

                val destDir = File(context.filesDir, "project_files/$projectId").also { it.mkdirs() }
                val destFile = File(destDir, "${UUID.randomUUID()}${if (ext.isNotBlank()) ".$ext" else ""}")

                context.contentResolver.openInputStream(uri)?.use { input ->
                    destFile.outputStream().use { output -> input.copyTo(output) }
                }

                val projectFile = ProjectFile(
                    id = UUID.randomUUID().toString(),
                    projectId = projectId,
                    name = displayName,
                    mimeType = mimeType,
                    filePath = destFile.absolutePath,
                    sizeBytes = destFile.length(),
                    addedAt = System.currentTimeMillis(),
                    notes = ""
                )
                projectFileRepository.saveFile(projectFile)
            } catch (e: Exception) {
                _uiState.update { it.copy(errorMessage = "Kunde inte lägga till filen: ${e.message}") }
            }
        }
    }

    fun requestDelete(file: ProjectFile) {
        _uiState.update { it.copy(showDeleteDialog = true, fileToDelete = file) }
    }

    fun cancelDelete() {
        _uiState.update { it.copy(showDeleteDialog = false, fileToDelete = null) }
    }

    fun confirmDelete() {
        val file = _uiState.value.fileToDelete ?: return
        _uiState.update { it.copy(showDeleteDialog = false, fileToDelete = null) }
        viewModelScope.launch {
            projectFileRepository.deleteFile(file.id)
            File(file.filePath).delete()
        }
    }

    fun requestRename(file: ProjectFile) {
        _uiState.update { it.copy(showRenameDialog = true, fileToRename = file) }
    }

    fun cancelRename() {
        _uiState.update { it.copy(showRenameDialog = false, fileToRename = null) }
    }

    fun confirmRename(newName: String) {
        val file = _uiState.value.fileToRename ?: return
        _uiState.update { it.copy(showRenameDialog = false, fileToRename = null) }
        viewModelScope.launch {
            projectFileRepository.saveFile(file.copy(name = newName.trim()))
        }
    }

    fun getFileProviderUri(file: ProjectFile): Uri? {
        val f = File(file.filePath)
        if (!f.exists()) return null
        return FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", f)
    }

    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }

    fun showFileNotFoundError() {
        _uiState.update { it.copy(errorMessage = "Filen hittades inte på enheten.") }
    }

    private fun queryDisplayName(uri: Uri): String? {
        return context.contentResolver.query(uri, arrayOf(OpenableColumns.DISPLAY_NAME), null, null, null)
            ?.use { cursor ->
                if (cursor.moveToFirst()) cursor.getString(0) else null
            }
    }
}
