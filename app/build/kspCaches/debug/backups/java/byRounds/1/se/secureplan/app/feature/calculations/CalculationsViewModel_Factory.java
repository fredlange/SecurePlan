package se.secureplan.app.feature.calculations;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import se.secureplan.app.core.domain.repository.CalculationRepository;
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
public final class CalculationsViewModel_Factory implements Factory<CalculationsViewModel> {
  private final Provider<SymbolPlacementRepository> symbolPlacementRepositoryProvider;

  private final Provider<ProductRepository> productRepositoryProvider;

  private final Provider<DrawingRepository> drawingRepositoryProvider;

  private final Provider<CalculationRepository> calculationRepositoryProvider;

  public CalculationsViewModel_Factory(
      Provider<SymbolPlacementRepository> symbolPlacementRepositoryProvider,
      Provider<ProductRepository> productRepositoryProvider,
      Provider<DrawingRepository> drawingRepositoryProvider,
      Provider<CalculationRepository> calculationRepositoryProvider) {
    this.symbolPlacementRepositoryProvider = symbolPlacementRepositoryProvider;
    this.productRepositoryProvider = productRepositoryProvider;
    this.drawingRepositoryProvider = drawingRepositoryProvider;
    this.calculationRepositoryProvider = calculationRepositoryProvider;
  }

  @Override
  public CalculationsViewModel get() {
    return newInstance(symbolPlacementRepositoryProvider.get(), productRepositoryProvider.get(), drawingRepositoryProvider.get(), calculationRepositoryProvider.get());
  }

  public static CalculationsViewModel_Factory create(
      Provider<SymbolPlacementRepository> symbolPlacementRepositoryProvider,
      Provider<ProductRepository> productRepositoryProvider,
      Provider<DrawingRepository> drawingRepositoryProvider,
      Provider<CalculationRepository> calculationRepositoryProvider) {
    return new CalculationsViewModel_Factory(symbolPlacementRepositoryProvider, productRepositoryProvider, drawingRepositoryProvider, calculationRepositoryProvider);
  }

  public static CalculationsViewModel newInstance(
      SymbolPlacementRepository symbolPlacementRepository, ProductRepository productRepository,
      DrawingRepository drawingRepository, CalculationRepository calculationRepository) {
    return new CalculationsViewModel(symbolPlacementRepository, productRepository, drawingRepository, calculationRepository);
  }
}
