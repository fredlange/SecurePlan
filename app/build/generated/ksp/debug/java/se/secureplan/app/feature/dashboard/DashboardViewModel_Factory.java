package se.secureplan.app.feature.dashboard;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import se.secureplan.app.core.domain.repository.DrawingRepository;
import se.secureplan.app.core.domain.repository.ProjectRepository;

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
public final class DashboardViewModel_Factory implements Factory<DashboardViewModel> {
  private final Provider<ProjectRepository> projectRepositoryProvider;

  private final Provider<DrawingRepository> drawingRepositoryProvider;

  public DashboardViewModel_Factory(Provider<ProjectRepository> projectRepositoryProvider,
      Provider<DrawingRepository> drawingRepositoryProvider) {
    this.projectRepositoryProvider = projectRepositoryProvider;
    this.drawingRepositoryProvider = drawingRepositoryProvider;
  }

  @Override
  public DashboardViewModel get() {
    return newInstance(projectRepositoryProvider.get(), drawingRepositoryProvider.get());
  }

  public static DashboardViewModel_Factory create(
      Provider<ProjectRepository> projectRepositoryProvider,
      Provider<DrawingRepository> drawingRepositoryProvider) {
    return new DashboardViewModel_Factory(projectRepositoryProvider, drawingRepositoryProvider);
  }

  public static DashboardViewModel newInstance(ProjectRepository projectRepository,
      DrawingRepository drawingRepository) {
    return new DashboardViewModel(projectRepository, drawingRepository);
  }
}
