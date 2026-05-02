package se.secureplan.app.feature.drawing;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import se.secureplan.app.core.domain.repository.CablePathRepository;
import se.secureplan.app.core.domain.repository.DrawingRepository;
import se.secureplan.app.core.domain.repository.SymbolPlacementRepository;
import se.secureplan.app.core.domain.repository.SymbolRepository;
import se.secureplan.app.core.domain.repository.ZoneRepository;

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
public final class DrawingViewModel_Factory implements Factory<DrawingViewModel> {
  private final Provider<DrawingRepository> drawingRepositoryProvider;

  private final Provider<SymbolPlacementRepository> placementRepositoryProvider;

  private final Provider<CablePathRepository> cableRepositoryProvider;

  private final Provider<ZoneRepository> zoneRepositoryProvider;

  private final Provider<SymbolRepository> symbolRepositoryProvider;

  public DrawingViewModel_Factory(Provider<DrawingRepository> drawingRepositoryProvider,
      Provider<SymbolPlacementRepository> placementRepositoryProvider,
      Provider<CablePathRepository> cableRepositoryProvider,
      Provider<ZoneRepository> zoneRepositoryProvider,
      Provider<SymbolRepository> symbolRepositoryProvider) {
    this.drawingRepositoryProvider = drawingRepositoryProvider;
    this.placementRepositoryProvider = placementRepositoryProvider;
    this.cableRepositoryProvider = cableRepositoryProvider;
    this.zoneRepositoryProvider = zoneRepositoryProvider;
    this.symbolRepositoryProvider = symbolRepositoryProvider;
  }

  @Override
  public DrawingViewModel get() {
    return newInstance(drawingRepositoryProvider.get(), placementRepositoryProvider.get(), cableRepositoryProvider.get(), zoneRepositoryProvider.get(), symbolRepositoryProvider.get());
  }

  public static DrawingViewModel_Factory create(
      Provider<DrawingRepository> drawingRepositoryProvider,
      Provider<SymbolPlacementRepository> placementRepositoryProvider,
      Provider<CablePathRepository> cableRepositoryProvider,
      Provider<ZoneRepository> zoneRepositoryProvider,
      Provider<SymbolRepository> symbolRepositoryProvider) {
    return new DrawingViewModel_Factory(drawingRepositoryProvider, placementRepositoryProvider, cableRepositoryProvider, zoneRepositoryProvider, symbolRepositoryProvider);
  }

  public static DrawingViewModel newInstance(DrawingRepository drawingRepository,
      SymbolPlacementRepository placementRepository, CablePathRepository cableRepository,
      ZoneRepository zoneRepository, SymbolRepository symbolRepository) {
    return new DrawingViewModel(drawingRepository, placementRepository, cableRepository, zoneRepository, symbolRepository);
  }
}
