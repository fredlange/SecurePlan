package se.secureplan.app.feature.projects

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import se.secureplan.app.core.domain.model.Project
import se.secureplan.app.core.domain.model.ProjectStatus
import se.secureplan.app.core.domain.model.SystemCategory
import se.secureplan.app.core.domain.repository.ProjectRepository
import java.util.UUID
import javax.inject.Inject

data class ProjectsUiState(
    val projects: List<Project> = emptyList(),
    val isLoading: Boolean = false,
    val searchQuery: String = "",
    val filterCategory: SystemCategory? = null,
    val showCreateDialog: Boolean = false,
    val errorMessage: String? = null
)

@HiltViewModel
class ProjectsViewModel @Inject constructor(
    private val repository: ProjectRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProjectsUiState())
    val uiState: StateFlow<ProjectsUiState> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    private val _filterCategory = MutableStateFlow<SystemCategory?>(null)

    init {
        observeProjects()
    }

    private fun observeProjects() {
        combine(_searchQuery, _filterCategory) { query, category ->
            Pair(query, category)
        }.flatMapLatest { (query, category) ->
            when {
                query.isNotBlank() -> repository.searchProjects(query)
                category != null   -> repository.getProjectsByCategory(category)
                else               -> repository.getAllProjects()
            }
        }.onEach { projects ->
            _uiState.update { it.copy(projects = projects, isLoading = false) }
        }.catch { e ->
            _uiState.update { it.copy(errorMessage = e.message, isLoading = false) }
        }.launchIn(viewModelScope)
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun onCategoryFilterChange(category: SystemCategory?) {
        _filterCategory.value = category
        _uiState.update { it.copy(filterCategory = category) }
    }

    fun showCreateDialog() = _uiState.update { it.copy(showCreateDialog = true) }
    fun hideCreateDialog() = _uiState.update { it.copy(showCreateDialog = false) }

    fun createProject(
        name: String,
        clientName: String,
        address: String,
        description: String,
        category: SystemCategory,
        installerName: String,
        installerCompany: String,
        installerEmail: String,
        installerPhone: String
    ) {
        viewModelScope.launch {
            val now = System.currentTimeMillis()
            val project = Project(
                id = UUID.randomUUID().toString(),
                name = name,
                clientName = clientName,
                address = address,
                description = description,
                systemCategory = category,
                status = ProjectStatus.PLANNING,
                createdAt = now,
                updatedAt = now,
                installerName = installerName,
                installerCompany = installerCompany,
                installerEmail = installerEmail,
                installerPhone = installerPhone,
                coverImageUri = null
            )
            repository.saveProject(project)
            hideCreateDialog()
        }
    }

    fun deleteProject(id: String) {
        viewModelScope.launch { repository.deleteProject(id) }
    }

    fun clearError() = _uiState.update { it.copy(errorMessage = null) }
}
