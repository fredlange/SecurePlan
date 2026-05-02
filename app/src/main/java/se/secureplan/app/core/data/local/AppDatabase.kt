package se.secureplan.app.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import se.secureplan.app.core.data.local.dao.*
import se.secureplan.app.core.data.local.entity.*

@Database(
    entities = [
        ProjectEntity::class,
        DrawingEntity::class,
        ProductEntity::class,
        SymbolEntity::class,
        SymbolPlacementEntity::class,
        CablePathEntity::class,
        ZoneEntity::class,
        GeoPhotoEntity::class,
        ProtocolTemplateEntity::class,
        ProtocolInstanceEntity::class,
        CalculationEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun projectDao(): ProjectDao
    abstract fun drawingDao(): DrawingDao
    abstract fun productDao(): ProductDao
    abstract fun symbolDao(): SymbolDao
    abstract fun symbolPlacementDao(): SymbolPlacementDao
    abstract fun cablePathDao(): CablePathDao
    abstract fun zoneDao(): ZoneDao
    abstract fun geoPhotoDao(): GeoPhotoDao
    abstract fun protocolTemplateDao(): ProtocolTemplateDao
    abstract fun protocolInstanceDao(): ProtocolInstanceDao
    abstract fun calculationDao(): CalculationDao

    companion object {
        const val DATABASE_NAME = "secureplan.db"
    }
}
