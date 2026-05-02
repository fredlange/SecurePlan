package se.secureplan.app.core.data.repository.impl;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
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
public final class GeoPhotoRepositoryImpl_Factory implements Factory<GeoPhotoRepositoryImpl> {
  private final Provider<GeoPhotoDao> daoProvider;

  public GeoPhotoRepositoryImpl_Factory(Provider<GeoPhotoDao> daoProvider) {
    this.daoProvider = daoProvider;
  }

  @Override
  public GeoPhotoRepositoryImpl get() {
    return newInstance(daoProvider.get());
  }

  public static GeoPhotoRepositoryImpl_Factory create(Provider<GeoPhotoDao> daoProvider) {
    return new GeoPhotoRepositoryImpl_Factory(daoProvider);
  }

  public static GeoPhotoRepositoryImpl newInstance(GeoPhotoDao dao) {
    return new GeoPhotoRepositoryImpl(dao);
  }
}
