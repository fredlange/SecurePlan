package se.secureplan.app.feature.protocols;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import se.secureplan.app.core.domain.repository.ProtocolInstanceRepository;
import se.secureplan.app.core.domain.repository.ProtocolTemplateRepository;

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
public final class ProtocolsViewModel_Factory implements Factory<ProtocolsViewModel> {
  private final Provider<ProtocolTemplateRepository> protocolTemplateRepositoryProvider;

  private final Provider<ProtocolInstanceRepository> protocolInstanceRepositoryProvider;

  public ProtocolsViewModel_Factory(
      Provider<ProtocolTemplateRepository> protocolTemplateRepositoryProvider,
      Provider<ProtocolInstanceRepository> protocolInstanceRepositoryProvider) {
    this.protocolTemplateRepositoryProvider = protocolTemplateRepositoryProvider;
    this.protocolInstanceRepositoryProvider = protocolInstanceRepositoryProvider;
  }

  @Override
  public ProtocolsViewModel get() {
    return newInstance(protocolTemplateRepositoryProvider.get(), protocolInstanceRepositoryProvider.get());
  }

  public static ProtocolsViewModel_Factory create(
      Provider<ProtocolTemplateRepository> protocolTemplateRepositoryProvider,
      Provider<ProtocolInstanceRepository> protocolInstanceRepositoryProvider) {
    return new ProtocolsViewModel_Factory(protocolTemplateRepositoryProvider, protocolInstanceRepositoryProvider);
  }

  public static ProtocolsViewModel newInstance(
      ProtocolTemplateRepository protocolTemplateRepository,
      ProtocolInstanceRepository protocolInstanceRepository) {
    return new ProtocolsViewModel(protocolTemplateRepository, protocolInstanceRepository);
  }
}
