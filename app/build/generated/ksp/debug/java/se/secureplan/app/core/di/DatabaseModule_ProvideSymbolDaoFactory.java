package se.secureplan.app.core.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import se.secureplan.app.core.data.local.AppDatabase;
import se.secureplan.app.core.data.local.dao.SymbolDao;

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
public final class DatabaseModule_ProvideSymbolDaoFactory implements Factory<SymbolDao> {
  private final Provider<AppDatabase> dbProvider;

  public DatabaseModule_ProvideSymbolDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public SymbolDao get() {
    return provideSymbolDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideSymbolDaoFactory create(Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideSymbolDaoFactory(dbProvider);
  }

  public static SymbolDao provideSymbolDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideSymbolDao(db));
  }
}
