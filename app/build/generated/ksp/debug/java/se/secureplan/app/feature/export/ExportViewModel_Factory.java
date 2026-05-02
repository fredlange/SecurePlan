package se.secureplan.app.feature.export;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class ExportViewModel_Factory implements Factory<ExportViewModel> {
  private final Provider<Context> contextProvider;

  private final Provider<ExportManager> exportManagerProvider;

  public ExportViewModel_Factory(Provider<Context> contextProvider,
      Provider<ExportManager> exportManagerProvider) {
    this.contextProvider = contextProvider;
    this.exportManagerProvider = exportManagerProvider;
  }

  @Override
  public ExportViewModel get() {
    return newInstance(contextProvider.get(), exportManagerProvider.get());
  }

  public static ExportViewModel_Factory create(Provider<Context> contextProvider,
      Provider<ExportManager> exportManagerProvider) {
    return new ExportViewModel_Factory(contextProvider, exportManagerProvider);
  }

  public static ExportViewModel newInstance(Context context, ExportManager exportManager) {
    return new ExportViewModel(context, exportManager);
  }
}
