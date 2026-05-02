package se.secureplan.app.feature.export;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import se.secureplan.app.core.domain.repository.CalculationRepository;
import se.secureplan.app.core.domain.repository.DrawingRepository;
import se.secureplan.app.core.domain.repository.GeoPhotoRepository;
import se.secureplan.app.core.domain.repository.ProductRepository;
import se.secureplan.app.core.domain.repository.ProjectRepository;
import se.secureplan.app.core.domain.repository.ProtocolInstanceRepository;
import se.secureplan.app.core.domain.repository.ProtocolTemplateRepository;
import se.secureplan.app.core.domain.repository.SymbolPlacementRepository;

@ScopeMetadata("javax.inject.Singleton")
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
public final class ExportManager_Factory implements Factory<ExportManager> {
  private final Provider<Context> contextProvider;

  private final Provider<ProjectRepository> projectRepositoryProvider;

  private final Provider<DrawingRepository> drawingRepositoryProvider;

  private final Provider<SymbolPlacementRepository> symbolPlacementRepositoryProvider;

  private final Provider<ProductRepository> productRepositoryProvider;

  private final Provider<CalculationRepository> calculationRepositoryProvider;

  private final Provider<GeoPhotoRepository> geoPhotoRepositoryProvider;

  private final Provider<ProtocolInstanceRepository> protocolInstanceRepositoryProvider;

  private final Provider<ProtocolTemplateRepository> protocolTemplateRepositoryProvider;

  public ExportManager_Factory(Provider<Context> contextProvider,
      Provider<ProjectRepository> projectRepositoryProvider,
      Provider<DrawingRepository> drawingRepositoryProvider,
      Provider<SymbolPlacementRepository> symbolPlacementRepositoryProvider,
      Provider<ProductRepository> productRepositoryProvider,
      Provider<CalculationRepository> calculationRepositoryProvider,
      Provider<GeoPhotoRepository> geoPhotoRepositoryProvider,
      Provider<ProtocolInstanceRepository> protocolInstanceRepositoryProvider,
      Provider<ProtocolTemplateRepository> protocolTemplateRepositoryProvider) {
    this.contextProvider = contextProvider;
    this.projectRepositoryProvider = projectRepositoryProvider;
    this.drawingRepositoryProvider = drawingRepositoryProvider;
    this.symbolPlacementRepositoryProvider = symbolPlacementRepositoryProvider;
    this.productRepositoryProvider = productRepositoryProvider;
    this.calculationRepositoryProvider = calculationRepositoryProvider;
    this.geoPhotoRepositoryProvider = geoPhotoRepositoryProvider;
    this.protocolInstanceRepositoryProvider = protocolInstanceRepositoryProvider;
    this.protocolTemplateRepositoryProvider = protocolTemplateRepositoryProvider;
  }

  @Override
  public ExportManager get() {
    return newInstance(contextProvider.get(), projectRepositoryProvider.get(), drawingRepositoryProvider.get(), symbolPlacementRepositoryProvider.get(), productRepositoryProvider.get(), calculationRepositoryProvider.get(), geoPhotoRepositoryProvider.get(), protocolInstanceRepositoryProvider.get(), protocolTemplateRepositoryProvider.get());
  }

  public static ExportManager_Factory create(Provider<Context> contextProvider,
      Provider<ProjectRepository> projectRepositoryProvider,
      Provider<DrawingRepository> drawingRepositoryProvider,
      Provider<SymbolPlacementRepository> symbolPlacementRepositoryProvider,
      Provider<ProductRepository> productRepositoryProvider,
      Provider<CalculationRepository> calculationRepositoryProvider,
      Provider<GeoPhotoRepository> geoPhotoRepositoryProvider,
      Provider<ProtocolInstanceRepository> protocolInstanceRepositoryProvider,
      Provider<ProtocolTemplateRepository> protocolTemplateRepositoryProvider) {
    return new ExportManager_Factory(contextProvider, projectRepositoryProvider, drawingRepositoryProvider, symbolPlacementRepositoryProvider, productRepositoryProvider, calculationRepositoryProvider, geoPhotoRepositoryProvider, protocolInstanceRepositoryProvider, protocolTemplateRepositoryProvider);
  }

  public static ExportManager newInstance(Context context, ProjectRepository projectRepository,
      DrawingRepository drawingRepository, SymbolPlacementRepository symbolPlacementRepository,
      ProductRepository productRepository, CalculationRepository calculationRepository,
      GeoPhotoRepository geoPhotoRepository, ProtocolInstanceRepository protocolInstanceRepository,
      ProtocolTemplateRepository protocolTemplateRepository) {
    return new ExportManager(context, projectRepository, drawingRepository, symbolPlacementRepository, productRepository, calculationRepository, geoPhotoRepository, protocolInstanceRepository, protocolTemplateRepository);
  }
}
