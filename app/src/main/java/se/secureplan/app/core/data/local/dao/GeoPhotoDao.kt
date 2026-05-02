package se.secureplan.app.core.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import se.secureplan.app.core.data.local.entity.GeoPhotoEntity

@Dao
interface GeoPhotoDao {
    @Query("SELECT * FROM geo_photos WHERE projectId = :projectId ORDER BY takenAt DESC")
    fun getPhotosForProject(projectId: String): Flow<List<GeoPhotoEntity>>

    @Query("SELECT * FROM geo_photos WHERE drawingId = :drawingId ORDER BY takenAt DESC")
    fun getPhotosForDrawing(drawingId: String): Flow<List<GeoPhotoEntity>>

    @Query("SELECT * FROM geo_photos WHERE id = :id")
    suspend fun getPhotoById(id: String): GeoPhotoEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhoto(photo: GeoPhotoEntity)

    @Update
    suspend fun updatePhoto(photo: GeoPhotoEntity)

    @Delete
    suspend fun deletePhoto(photo: GeoPhotoEntity)

    @Query("DELETE FROM geo_photos WHERE id = :id")
    suspend fun deletePhotoById(id: String)

    @Query("SELECT COUNT(*) FROM geo_photos WHERE projectId = :projectId")
    fun getPhotoCountForProject(projectId: String): Flow<Int>
}
