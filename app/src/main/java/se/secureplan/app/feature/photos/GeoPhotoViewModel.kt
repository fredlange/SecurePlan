package se.secureplan.app.feature.photos

import android.content.Context
import android.net.Uri
import androidx.exifinterface.media.ExifInterface
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import se.secureplan.app.core.domain.model.GeoPhoto
import se.secureplan.app.core.domain.repository.GeoPhotoRepository
import java.util.UUID
import javax.inject.Inject

data class GeoPhotoUiState(
    val photos: List<GeoPhoto> = emptyList(),
    val isLoading: Boolean = true
)

@HiltViewModel
class GeoPhotoViewModel @Inject constructor(
    private val geoPhotoRepository: GeoPhotoRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _uiState = MutableStateFlow(GeoPhotoUiState())
    val uiState: StateFlow<GeoPhotoUiState> = _uiState.asStateFlow()

    private var currentProjectId: String? = null

    fun loadPhotos(projectId: String) {
        if (currentProjectId == projectId) return
        currentProjectId = projectId
        viewModelScope.launch {
            geoPhotoRepository.getPhotosForProject(projectId)
                .collect { photos ->
                    _uiState.update { it.copy(photos = photos, isLoading = false) }
                }
        }
    }

    fun savePhotoFromUri(
        projectId: String,
        uri: Uri,
        drawingId: String? = null,
        caption: String = ""
    ) {
        viewModelScope.launch {
            val (lat, lon) = extractGpsFromUri(uri)
            val photo = GeoPhoto(
                id        = UUID.randomUUID().toString(),
                projectId = projectId,
                drawingId = drawingId,
                photoUri  = uri.toString(),
                latitude  = lat,
                longitude = lon,
                caption   = caption,
                takenAt   = System.currentTimeMillis(),
                xNorm     = null,
                yNorm     = null
            )
            geoPhotoRepository.savePhoto(photo)
        }
    }

    fun deletePhoto(id: String) {
        viewModelScope.launch {
            geoPhotoRepository.deletePhoto(id)
        }
    }

    fun updateCaption(photo: GeoPhoto, newCaption: String) {
        viewModelScope.launch {
            geoPhotoRepository.savePhoto(photo.copy(caption = newCaption))
        }
    }

    // ─── GPS helpers ─────────────────────────────────────────────────────────

    private fun extractGpsFromUri(uri: Uri): Pair<Double?, Double?> {
        return try {
            context.contentResolver.openInputStream(uri)?.use { stream ->
                val exif = ExifInterface(stream)
                val lat = parseDms(
                    exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE),
                    exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF)
                )
                val lon = parseDms(
                    exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE),
                    exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF)
                )
                Pair(lat, lon)
            } ?: Pair(null, null)
        } catch (e: Exception) {
            Pair(null, null)
        }
    }

    private fun parseDms(dms: String?, ref: String?): Double? {
        if (dms == null) return null
        return try {
            val parts = dms.split(",")
            fun rational(s: String): Double {
                val (num, den) = s.trim().split("/")
                return num.toDouble() / den.toDouble()
            }
            val deg = rational(parts[0])
            val min = rational(parts[1])
            val sec = rational(parts[2])
            val dd = deg + min / 60.0 + sec / 3600.0
            if (ref == "S" || ref == "W") -dd else dd
        } catch (e: Exception) {
            null
        }
    }
}
