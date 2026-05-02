package se.secureplan.app.core.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import se.secureplan.app.core.data.local.AppDatabase;
import se.secureplan.app.core.data.local.dao.DrawingDao;

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
public final class DatabaseModule_ProvideDrawingDaoFactory implements Factory<DrawingDao> {
  private final Provider<AppDatabase> dbProvider;

  public DatabaseModule_ProvideDrawingDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public DrawingDao get() {
    return provideDrawingDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideDrawingDaoFactory create(Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideDrawingDaoFactory(dbProvider);
  }

  public static DrawingDao provideDrawingDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideDrawingDao(db));
  }
}
