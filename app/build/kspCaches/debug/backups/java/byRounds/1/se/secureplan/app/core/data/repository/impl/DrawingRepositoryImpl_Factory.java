package se.secureplan.app.core.data.repository.impl;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
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
public final class DrawingRepositoryImpl_Factory implements Factory<DrawingRepositoryImpl> {
  private final Provider<DrawingDao> daoProvider;

  public DrawingRepositoryImpl_Factory(Provider<DrawingDao> daoProvider) {
    this.daoProvider = daoProvider;
  }

  @Override
  public DrawingRepositoryImpl get() {
    return newInstance(daoProvider.get());
  }

  public static DrawingRepositoryImpl_Factory create(Provider<DrawingDao> daoProvider) {
    return new DrawingRepositoryImpl_Factory(daoProvider);
  }

  public static DrawingRepositoryImpl newInstance(DrawingDao dao) {
    return new DrawingRepositoryImpl(dao);
  }
}
