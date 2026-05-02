package se.secureplan.app.core.data.seed;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import se.secureplan.app.core.domain.repository.ProductRepository;
import se.secureplan.app.core.domain.repository.ProtocolTemplateRepository;
import se.secureplan.app.core.domain.repository.SymbolRepository;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class SeedDataProvider_Factory implements Factory<SeedDataProvider> {
  private final Provider<Context> contextProvider;

  private final Provider<SymbolRepository> symbolRepositoryProvider;

  private final Provider<ProtocolTemplateRepository> templateRepositoryProvider;

  private final Provider<ProductRepository> productRepositoryProvider;

  public SeedDataProvider_Factory(Provider<Context> contextProvider,
      Provider<SymbolRepository> symbolRepositoryProvider,
      Provider<ProtocolTemplateRepository> templateRepositoryProvider,
      Provider<ProductRepository> productRepositoryProvider) {
    this.contextProvider = contextProvider;
    this.symbolRepositoryProvider = symbolRepositoryProvider;
    this.templateRepositoryProvider = templateRepositoryProvider;
    this.productRepositoryProvider = productRepositoryProvider;
  }

  @Override
  public SeedDataProvider get() {
    return newInstance(contextProvider.get(), symbolRepositoryProvider.get(), templateRepositoryProvider.get(), productRepositoryProvider.get());
  }

  public static SeedDataProvider_Factory create(Provider<Context> contextProvider,
      Provider<SymbolRepository> symbolRepositoryProvider,
      Provider<ProtocolTemplateRepository> templateRepositoryProvider,
      Provider<ProductRepository> productRepositoryProvider) {
    return new SeedDataProvider_Factory(contextProvider, symbolRepositoryProvider, templateRepositoryProvider, productRepositoryProvider);
  }

  public static SeedDataProvider newInstance(Context context, SymbolRepository symbolRepository,
      ProtocolTemplateRepository templateRepository, ProductRepository productRepository) {
    return new SeedDataProvider(context, symbolRepository, templateRepository, productRepository);
  }
}
