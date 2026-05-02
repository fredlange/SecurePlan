package se.secureplan.app.core.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import se.secureplan.app.core.data.local.AppDatabase;
import se.secureplan.app.core.data.local.dao.CablePathDao;

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
public final class DatabaseModule_ProvideCablePathDaoFactory implements Factory<CablePathDao> {
  private final Provider<AppDatabase> dbProvider;

  public DatabaseModule_ProvideCablePathDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public CablePathDao get() {
    return provideCablePathDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideCablePathDaoFactory create(Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideCablePathDaoFactory(dbProvider);
  }

  public static CablePathDao provideCablePathDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideCablePathDao(db));
  }
}
