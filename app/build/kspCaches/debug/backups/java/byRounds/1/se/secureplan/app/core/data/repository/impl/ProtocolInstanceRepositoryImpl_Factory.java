package se.secureplan.app.core.data.repository.impl;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import se.secureplan.app.core.data.local.dao.ProtocolInstanceDao;

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
public final class ProtocolInstanceRepositoryImpl_Factory implements Factory<ProtocolInstanceRepositoryImpl> {
  private final Provider<ProtocolInstanceDao> daoProvider;

  public ProtocolInstanceRepositoryImpl_Factory(Provider<ProtocolInstanceDao> daoProvider) {
    this.daoProvider = daoProvider;
  }

  @Override
  public ProtocolInstanceRepositoryImpl get() {
    return newInstance(daoProvider.get());
  }

  public static ProtocolInstanceRepositoryImpl_Factory create(
      Provider<ProtocolInstanceDao> daoProvider) {
    return new ProtocolInstanceRepositoryImpl_Factory(daoProvider);
  }

  public static ProtocolInstanceRepositoryImpl newInstance(ProtocolInstanceDao dao) {
    return new ProtocolInstanceRepositoryImpl(dao);
  }
}
