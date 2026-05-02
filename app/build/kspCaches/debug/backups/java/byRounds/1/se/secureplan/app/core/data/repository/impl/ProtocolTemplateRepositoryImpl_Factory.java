package se.secureplan.app.core.data.repository.impl;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import se.secureplan.app.core.data.local.dao.ProtocolTemplateDao;

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
public final class ProtocolTemplateRepositoryImpl_Factory implements Factory<ProtocolTemplateRepositoryImpl> {
  private final Provider<ProtocolTemplateDao> daoProvider;

  public ProtocolTemplateRepositoryImpl_Factory(Provider<ProtocolTemplateDao> daoProvider) {
    this.daoProvider = daoProvider;
  }

  @Override
  public ProtocolTemplateRepositoryImpl get() {
    return newInstance(daoProvider.get());
  }

  public static ProtocolTemplateRepositoryImpl_Factory create(
      Provider<ProtocolTemplateDao> daoProvider) {
    return new ProtocolTemplateRepositoryImpl_Factory(daoProvider);
  }

  public static ProtocolTemplateRepositoryImpl newInstance(ProtocolTemplateDao dao) {
    return new ProtocolTemplateRepositoryImpl(dao);
  }
}
