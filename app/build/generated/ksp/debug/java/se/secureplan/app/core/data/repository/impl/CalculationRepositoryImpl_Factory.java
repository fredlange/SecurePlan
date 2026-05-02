package se.secureplan.app.core.data.repository.impl;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import se.secureplan.app.core.data.local.dao.CalculationDao;

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
public final class CalculationRepositoryImpl_Factory implements Factory<CalculationRepositoryImpl> {
  private final Provider<CalculationDao> daoProvider;

  public CalculationRepositoryImpl_Factory(Provider<CalculationDao> daoProvider) {
    this.daoProvider = daoProvider;
  }

  @Override
  public CalculationRepositoryImpl get() {
    return newInstance(daoProvider.get());
  }

  public static CalculationRepositoryImpl_Factory create(Provider<CalculationDao> daoProvider) {
    return new CalculationRepositoryImpl_Factory(daoProvider);
  }

  public static CalculationRepositoryImpl newInstance(CalculationDao dao) {
    return new CalculationRepositoryImpl(dao);
  }
}
