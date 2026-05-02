package se.secureplan.app.core.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import se.secureplan.app.core.data.local.AppDatabase;
import se.secureplan.app.core.data.local.dao.CalculationDao;

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
public final class DatabaseModule_ProvideCalculationDaoFactory implements Factory<CalculationDao> {
  private final Provider<AppDatabase> dbProvider;

  public DatabaseModule_ProvideCalculationDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public CalculationDao get() {
    return provideCalculationDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideCalculationDaoFactory create(
      Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideCalculationDaoFactory(dbProvider);
  }

  public static CalculationDao provideCalculationDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideCalculationDao(db));
  }
}
