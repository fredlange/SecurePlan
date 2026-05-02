package se.secureplan.app.feature.protocols

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import se.secureplan.app.core.domain.model.ProtocolInstance
import se.secureplan.app.core.domain.model.ProtocolTemplate
import se.secureplan.app.core.domain.repository.ProtocolInstanceRepository
import se.secureplan.app.core.domain.repository.ProtocolTemplateRepository
import java.util.UUID
import javax.inject.Inject

data class ProtocolField(
    val id: String,
    val label: String,
    val type: String,
    val required: Boolean
)

data class ProtocolUiState(
    val template: ProtocolTemplate? = null,
    val instance: ProtocolInstance? = null,
    val fieldValues: Map<String, String> = emptyMap(),
    val fields: List<ProtocolField> = emptyList(),
    val technicianName: String = "",
    val signatureBase64: String = "",
    val isLoading: Boolean = true,
    val isSaved: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class ProtocolViewModel @Inject constructor(
    private val protocolTemplateRepository: ProtocolTemplateRepository,
    private val protocolInstanceRepository: ProtocolInstanceRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProtocolUiState())
    val uiState: StateFlow<ProtocolUiState> = _uiState.asStateFlow()

    private val gson = Gson()

    fun loadOrCreateInstance(templateId: String?, projectId: String, existingInstanceId: String?) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                if (existingInstanceId != null) {
                    protocolInstanceRepository.getInstanceById(existingInstanceId)
                        .filterNotNull()
                        .first()
                        .let { instance ->
                            val allTemplates = protocolTemplateRepository.getAllTemplates().first()
                            val template = allTemplates.find { it.id == instance.templateId }
                            val fields = parseFields(template?.fieldsJson ?: "[]")
                            val values = parseValues(instance.valuesJson)
                            _uiState.update {
                                it.copy(
                                    template = template,
                                    instance = instance,
                                    fields = fields,
                                    fieldValues = values,
                                    technicianName = instance.signedBy ?: "",
                                    isLoading = false
                                )
                            }
                        }
                } else if (templateId != null) {
                    val allTemplates = protocolTemplateRepository.getAllTemplates().first()
                    val template = allTemplates.find { it.id == templateId }
                    val fields = parseFields(template?.fieldsJson ?: "[]")
                    val now = System.currentTimeMillis()
                    val instance = ProtocolInstance(
                        id = UUID.randomUUID().toString(),
                        projectId = projectId,
                        templateId = templateId,
                        valuesJson = "{}",
                        status = "DRAFT",
                        signedBy = null,
                        signedAt = null,
                        createdAt = now,
                        updatedAt = now
                    )
                    _uiState.update {
                        it.copy(
                            template = template,
                            instance = instance,
                            fields = fields,
                            fieldValues = emptyMap(),
                            isLoading = false
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }

    fun updateFieldValue(fieldId: String, value: String) {
        _uiState.update { state ->
            state.copy(fieldValues = state.fieldValues + (fieldId to value))
        }
    }

    fun updateTechnicianName(name: String) {
        _uiState.update { it.copy(technicianName = name) }
    }

    fun updateSignature(base64: String) {
        _uiState.update { it.copy(signatureBase64 = base64) }
    }

    fun saveAsDraft() = saveInstance("DRAFT")

    fun saveAsCompleted() = saveInstance("COMPLETED")

    private fun saveInstance(status: String) {
        val state = _uiState.value
        val instance = state.instance ?: return
        viewModelScope.launch {
            try {
                val now = System.currentTimeMillis()
                val values = state.fieldValues.toMutableMap()
                if (state.signatureBase64.isNotEmpty()) {
                    values["_signature"] = state.signatureBase64
                }
                val updatedInstance = instance.copy(
                    valuesJson = gson.toJson(values),
                    status = status,
                    signedBy = if (status == "COMPLETED") state.technicianName else instance.signedBy,
                    signedAt = if (status == "COMPLETED") now else instance.signedAt,
                    updatedAt = now
                )
                protocolInstanceRepository.saveInstance(updatedInstance)
                _uiState.update { it.copy(instance = updatedInstance, isSaved = true) }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            }
        }
    }

    private fun parseFields(json: String): List<ProtocolField> {
        return try {
            val type = object : TypeToken<List<Map<String, Any>>>() {}.type
            val raw: List<Map<String, Any>> = gson.fromJson(json, type) ?: emptyList()
            raw.map { m ->
                ProtocolField(
                    id = m["id"]?.toString() ?: "",
                    label = m["label"]?.toString() ?: "",
                    type = m["type"]?.toString() ?: "text",
                    required = m["required"]?.toString()?.toBoolean() ?: false
                )
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    private fun parseValues(json: String): Map<String, String> {
        return try {
            val type = object : TypeToken<Map<String, String>>() {}.type
            gson.fromJson(json, type) ?: emptyMap()
        } catch (e: Exception) {
            emptyMap()
        }
    }
}
