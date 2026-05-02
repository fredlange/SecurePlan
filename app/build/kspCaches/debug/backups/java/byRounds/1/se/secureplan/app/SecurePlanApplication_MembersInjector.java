package se.secureplan.app;

import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import se.secureplan.app.core.data.seed.SeedDataProvider;

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
public final class SecurePlanApplication_MembersInjector implements MembersInjector<SecurePlanApplication> {
  private final Provider<SeedDataProvider> seedDataProvider;

  public SecurePlanApplication_MembersInjector(Provider<SeedDataProvider> seedDataProvider) {
    this.seedDataProvider = seedDataProvider;
  }

  public static MembersInjector<SecurePlanApplication> create(
      Provider<SeedDataProvider> seedDataProvider) {
    return new SecurePlanApplication_MembersInjector(seedDataProvider);
  }

  @Override
  public void injectMembers(SecurePlanApplication instance) {
    injectSeedDataProvider(instance, seedDataProvider.get());
  }

  @InjectedFieldSignature("se.secureplan.app.SecurePlanApplication.seedDataProvider")
  public static void injectSeedDataProvider(SecurePlanApplication instance,
      SeedDataProvider seedDataProvider) {
    instance.seedDataProvider = seedDataProvider;
  }
}
