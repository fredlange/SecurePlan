package se.secureplan.app.core.data.repository.impl;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import se.secureplan.app.core.data.local.dao.CablePathDao;

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
public final class CablePathRepositoryImpl_Factory implements Factory<CablePathRepositoryImpl> {
  private final Provider<CablePathDao> daoProvider;

  public CablePathRepositoryImpl_Factory(Provider<CablePathDao> daoProvider) {
    this.daoProvider = daoProvider;
  }

  @Override
  public CablePathRepositoryImpl get() {
    return newInstance(daoProvider.get());
  }

  public static CablePathRepositoryImpl_Factory create(Provider<CablePathDao> daoProvider) {
    return new CablePathRepositoryImpl_Factory(daoProvider);
  }

  public static CablePathRepositoryImpl newInstance(CablePathDao dao) {
    return new CablePathRepositoryImpl(dao);
  }
}
