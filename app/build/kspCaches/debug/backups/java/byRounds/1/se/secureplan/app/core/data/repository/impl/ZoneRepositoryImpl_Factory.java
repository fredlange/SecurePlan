package se.secureplan.app.core.data.repository.impl;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import se.secureplan.app.core.data.local.dao.ZoneDao;

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
public final class ZoneRepositoryImpl_Factory implements Factory<ZoneRepositoryImpl> {
  private final Provider<ZoneDao> daoProvider;

  public ZoneRepositoryImpl_Factory(Provider<ZoneDao> daoProvider) {
    this.daoProvider = daoProvider;
  }

  @Override
  public ZoneRepositoryImpl get() {
    return newInstance(daoProvider.get());
  }

  public static ZoneRepositoryImpl_Factory create(Provider<ZoneDao> daoProvider) {
    return new ZoneRepositoryImpl_Factory(daoProvider);
  }

  public static ZoneRepositoryImpl newInstance(ZoneDao dao) {
    return new ZoneRepositoryImpl(dao);
  }
}
