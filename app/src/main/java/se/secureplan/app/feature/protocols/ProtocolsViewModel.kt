package se.secureplan.app.feature.protocols

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import se.secureplan.app.core.domain.model.ProtocolInstance
import se.secureplan.app.core.domain.model.ProtocolTemplate
import se.secureplan.app.core.domain.repository.ProtocolInstanceRepository
import se.secureplan.app.core.domain.repository.ProtocolTemplateRepository
import javax.inject.Inject

data class ProtocolsUiState(
    val instances: List<ProtocolInstance> = emptyList(),
    val templates: List<ProtocolTemplate> = emptyList(),
    val isLoading: Boolean = true,
    val showTemplatePicker: Boolean = false
)

@HiltViewModel
class ProtocolsViewModel @Inject constructor(
    private val protocolTemplateRepository: ProtocolTemplateRepository,
    private val protocolInstanceRepository: ProtocolInstanceRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProtocolsUiState())
    val uiState: StateFlow<ProtocolsUiState> = _uiState.asStateFlow()

    private var currentProjectId: String? = null

    fun loadForProject(projectId: String) {
        if (currentProjectId == projectId) return
        currentProjectId = projectId
        viewModelScope.launch {
            combine(
                protocolInstanceRepository.getInstancesForProject(projectId),
                protocolTemplateRepository.getAllTemplates()
            ) { instances, templates -> Pair(instances, templates) }
                .collect { (instances, templates) ->
                    _uiState.update { it.copy(instances = instances, templates = templates, isLoading = false) }
                }
        }
    }

    fun showTemplatePicker() = _uiState.update { it.copy(showTemplatePicker = true) }
    fun hideTemplatePicker() = _uiState.update { it.copy(showTemplatePicker = false) }

    fun deleteInstance(id: String) {
        viewModelScope.launch { protocolInstanceRepository.deleteInstance(id) }
    }
}
