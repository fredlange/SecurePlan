package se.secureplan.app.feature.components;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import se.secureplan.app.core.domain.repository.DrawingRepository;
import se.secureplan.app.core.domain.repository.ProductRepository;
import se.secureplan.app.core.domain.repository.SymbolPlacementRepository;

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
public final class ComponentSummaryViewModel_Factory implements Factory<ComponentSummaryViewModel> {
  private final Provider<SymbolPlacementRepository> symbolPlacementRepositoryProvider;

  private final Provider<ProductRepository> productRepositoryProvider;

  private final Provider<DrawingRepository> drawingRepositoryProvider;

  public ComponentSummaryViewModel_Factory(
      Provider<SymbolPlacementRepository> symbolPlacementRepositoryProvider,
      Provider<ProductRepository> productRepositoryProvider,
      Provider<DrawingRepository> drawingRepositoryProvider) {
    this.symbolPlacementRepositoryProvider = symbolPlacementRepositoryProvider;
    this.productRepositoryProvider = productRepositoryProvider;
    this.drawingRepositoryProvider = drawingRepositoryProvider;
  }

  @Override
  public ComponentSummaryViewModel get() {
    return newInstance(symbolPlacementRepositoryProvider.get(), productRepositoryProvider.get(), drawingRepositoryProvider.get());
  }

  public static ComponentSummaryViewModel_Factory create(
      Provider<SymbolPlacementRepository> symbolPlacementRepositoryProvider,
      Provider<ProductRepository> productRepositoryProvider,
      Provider<DrawingRepository> drawingRepositoryProvider) {
    return new ComponentSummaryViewModel_Factory(symbolPlacementRepositoryProvider, productRepositoryProvider, drawingRepositoryProvider);
  }

  public static ComponentSummaryViewModel newInstance(
      SymbolPlacementRepository symbolPlacementRepository, ProductRepository productRepository,
      DrawingRepository drawingRepository) {
    return new ComponentSummaryViewModel(symbolPlacementRepository, productRepository, drawingRepository);
  }
}
