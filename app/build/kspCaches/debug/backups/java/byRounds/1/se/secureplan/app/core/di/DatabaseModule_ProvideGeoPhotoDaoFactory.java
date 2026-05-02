package se.secureplan.app.core.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import se.secureplan.app.core.data.local.AppDatabase;
import se.secureplan.app.core.data.local.dao.GeoPhotoDao;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation"
})
public final class DatabaseModule_ProvideGeoPhotoDaoFactory implements Factory<GeoPhotoDao> {
  private final Provider<AppDatabase> dbProvider;

  public DatabaseModule_ProvideGeoPhotoDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public GeoPhotoDao get() {
    return provideGeoPhotoDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideGeoPhotoDaoFactory create(Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideGeoPhotoDaoFactory(dbProvider);
  }

  public static GeoPhotoDao provideGeoPhotoDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideGeoPhotoDao(db));
  }
}
