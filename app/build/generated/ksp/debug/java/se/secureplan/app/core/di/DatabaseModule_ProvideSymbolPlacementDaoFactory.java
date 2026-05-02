package se.secureplan.app.core.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import se.secureplan.app.core.data.local.AppDatabase;
import se.secureplan.app.core.data.local.dao.SymbolPlacementDao;

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
public final class DatabaseModule_ProvideSymbolPlacementDaoFactory implements Factory<SymbolPlacementDao> {
  private final Provider<AppDatabase> dbProvider;

  public DatabaseModule_ProvideSymbolPlacementDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public SymbolPlacementDao get() {
    return provideSymbolPlacementDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideSymbolPlacementDaoFactory create(
      Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideSymbolPlacementDaoFactory(dbProvider);
  }

  public static SymbolPlacementDao provideSymbolPlacementDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideSymbolPlacementDao(db));
  }
}
