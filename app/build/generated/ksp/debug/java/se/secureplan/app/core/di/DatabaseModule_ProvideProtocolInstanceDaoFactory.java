package se.secureplan.app.core.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import se.secureplan.app.core.data.local.AppDatabase;
import se.secureplan.app.core.data.local.dao.ProtocolInstanceDao;

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
public final class DatabaseModule_ProvideProtocolInstanceDaoFactory implements Factory<ProtocolInstanceDao> {
  private final Provider<AppDatabase> dbProvider;

  public DatabaseModule_ProvideProtocolInstanceDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public ProtocolInstanceDao get() {
    return provideProtocolInstanceDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideProtocolInstanceDaoFactory create(
      Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideProtocolInstanceDaoFactory(dbProvider);
  }

  public static ProtocolInstanceDao provideProtocolInstanceDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideProtocolInstanceDao(db));
  }
}
