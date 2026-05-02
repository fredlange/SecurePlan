package se.secureplan.app.core.data.repository.impl;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import se.secureplan.app.core.data.local.dao.SymbolPlacementDao;

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
public final class SymbolPlacementRepositoryImpl_Factory implements Factory<SymbolPlacementRepositoryImpl> {
  private final Provider<SymbolPlacementDao> daoProvider;

  public SymbolPlacementRepositoryImpl_Factory(Provider<SymbolPlacementDao> daoProvider) {
    this.daoProvider = daoProvider;
  }

  @Override
  public SymbolPlacementRepositoryImpl get() {
    return newInstance(daoProvider.get());
  }

  public static SymbolPlacementRepositoryImpl_Factory create(
      Provider<SymbolPlacementDao> daoProvider) {
    return new SymbolPlacementRepositoryImpl_Factory(daoProvider);
  }

  public static SymbolPlacementRepositoryImpl newInstance(SymbolPlacementDao dao) {
    return new SymbolPlacementRepositoryImpl(dao);
  }
}
