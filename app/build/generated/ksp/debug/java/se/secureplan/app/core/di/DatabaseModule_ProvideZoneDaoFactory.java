package se.secureplan.app.core.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import se.secureplan.app.core.data.local.AppDatabase;
import se.secureplan.app.core.data.local.dao.ZoneDao;

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
public final class DatabaseModule_ProvideZoneDaoFactory implements Factory<ZoneDao> {
  private final Provider<AppDatabase> dbProvider;

  public DatabaseModule_ProvideZoneDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public ZoneDao get() {
    return provideZoneDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideZoneDaoFactory create(Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideZoneDaoFactory(dbProvider);
  }

  public static ZoneDao provideZoneDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideZoneDao(db));
  }
}
