package se.secureplan.app.core.data.repository.impl;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import se.secureplan.app.core.data.local.dao.SymbolDao;

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
public final class SymbolRepositoryImpl_Factory implements Factory<SymbolRepositoryImpl> {
  private final Provider<SymbolDao> daoProvider;

  public SymbolRepositoryImpl_Factory(Provider<SymbolDao> daoProvider) {
    this.daoProvider = daoProvider;
  }

  @Override
  public SymbolRepositoryImpl get() {
    return newInstance(daoProvider.get());
  }

  public static SymbolRepositoryImpl_Factory create(Provider<SymbolDao> daoProvider) {
    return new SymbolRepositoryImpl_Factory(daoProvider);
  }

  public static SymbolRepositoryImpl newInstance(SymbolDao dao) {
    return new SymbolRepositoryImpl(dao);
  }
}
