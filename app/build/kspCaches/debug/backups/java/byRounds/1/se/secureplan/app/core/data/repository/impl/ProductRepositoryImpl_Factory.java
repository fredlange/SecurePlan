package se.secureplan.app.core.data.repository.impl;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import se.secureplan.app.core.data.local.dao.ProductDao;

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
public final class ProductRepositoryImpl_Factory implements Factory<ProductRepositoryImpl> {
  private final Provider<ProductDao> daoProvider;

  public ProductRepositoryImpl_Factory(Provider<ProductDao> daoProvider) {
    this.daoProvider = daoProvider;
  }

  @Override
  public ProductRepositoryImpl get() {
    return newInstance(daoProvider.get());
  }

  public static ProductRepositoryImpl_Factory create(Provider<ProductDao> daoProvider) {
    return new ProductRepositoryImpl_Factory(daoProvider);
  }

  public static ProductRepositoryImpl newInstance(ProductDao dao) {
    return new ProductRepositoryImpl(dao);
  }
}
