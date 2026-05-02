package se.secureplan.app.feature.photos;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import se.secureplan.app.core.domain.repository.GeoPhotoRepository;

@ScopeMetadata
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class GeoPhotoViewModel_Factory implements Factory<GeoPhotoViewModel> {
  private final Provider<GeoPhotoRepository> geoPhotoRepositoryProvider;

  private final Provider<Context> contextProvider;

  public GeoPhotoViewModel_Factory(Provider<GeoPhotoRepository> geoPhotoRepositoryProvider,
      Provider<Context> contextProvider) {
    this.geoPhotoRepositoryProvider = geoPhotoRepositoryProvider;
    this.contextProvider = contextProvider;
  }

  @Override
  public GeoPhotoViewModel get() {
    return newInstance(geoPhotoRepositoryProvider.get(), contextProvider.get());
  }

  public static GeoPhotoViewModel_Factory create(
      Provider<GeoPhotoRepository> geoPhotoRepositoryProvider, Provider<Context> contextProvider) {
    return new GeoPhotoViewModel_Factory(geoPhotoRepositoryProvider, contextProvider);
  }

  public static GeoPhotoViewModel newInstance(GeoPhotoRepository geoPhotoRepository,
      Context context) {
    return new GeoPhotoViewModel(geoPhotoRepository, context);
  }
}
