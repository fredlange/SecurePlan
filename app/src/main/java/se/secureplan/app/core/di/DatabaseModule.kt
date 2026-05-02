package se.secureplan.app.core.di

import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import se.secureplan.app.core.data.local.AppDatabase
import se.secureplan.app.core.data.local.dao.*
import se.secureplan.app.core.data.repository.impl.*
import se.secureplan.app.core.domain.repository.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, AppDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Provides fun provideProjectDao(db: AppDatabase): ProjectDao = db.projectDao()
    @Provides fun provideDrawingDao(db: AppDatabase): DrawingDao = db.drawingDao()
    @Provides fun provideProductDao(db: AppDatabase): ProductDao = db.productDao()
    @Provides fun provideSymbolDao(db: AppDatabase): SymbolDao = db.symbolDao()
    @Provides fun provideSymbolPlacementDao(db: AppDatabase): SymbolPlacementDao = db.symbolPlacementDao()
    @Provides fun provideCablePathDao(db: AppDatabase): CablePathDao = db.cablePathDao()
    @Provides fun provideZoneDao(db: AppDatabase): ZoneDao = db.zoneDao()
    @Provides fun provideGeoPhotoDao(db: AppDatabase): GeoPhotoDao = db.geoPhotoDao()
    @Provides fun provideProtocolTemplateDao(db: AppDatabase): ProtocolTemplateDao = db.protocolTemplateDao()
    @Provides fun provideProtocolInstanceDao(db: AppDatabase): ProtocolInstanceDao = db.protocolInstanceDao()
    @Provides fun provideCalculationDao(db: AppDatabase): CalculationDao = db.calculationDao()
    @Provides fun provideProjectFileDao(db: AppDatabase): ProjectFileDao = db.projectFileDao()
}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds @Singleton
    abstract fun bindProjectRepository(impl: ProjectRepositoryImpl): ProjectRepository

    @Binds @Singleton
    abstract fun bindDrawingRepository(impl: DrawingRepositoryImpl): DrawingRepository

    @Binds @Singleton
    abstract fun bindSymbolPlacementRepository(impl: SymbolPlacementRepositoryImpl): SymbolPlacementRepository

    @Binds @Singleton
    abstract fun bindCablePathRepository(impl: CablePathRepositoryImpl): CablePathRepository

    @Binds @Singleton
    abstract fun bindZoneRepository(impl: ZoneRepositoryImpl): ZoneRepository

    @Binds @Singleton
    abstract fun bindProductRepository(impl: ProductRepositoryImpl): ProductRepository

    @Binds @Singleton
    abstract fun bindSymbolRepository(impl: SymbolRepositoryImpl): SymbolRepository

    @Binds @Singleton
    abstract fun bindGeoPhotoRepository(impl: GeoPhotoRepositoryImpl): GeoPhotoRepository

    @Binds @Singleton
    abstract fun bindProtocolTemplateRepository(impl: ProtocolTemplateRepositoryImpl): ProtocolTemplateRepository

    @Binds @Singleton
    abstract fun bindProtocolInstanceRepository(impl: ProtocolInstanceRepositoryImpl): ProtocolInstanceRepository

    @Binds @Singleton
    abstract fun bindCalculationRepository(impl: CalculationRepositoryImpl): CalculationRepository

    @Binds @Singleton
    abstract fun bindProjectFileRepository(impl: ProjectFileRepositoryImpl): ProjectFileRepository
}
