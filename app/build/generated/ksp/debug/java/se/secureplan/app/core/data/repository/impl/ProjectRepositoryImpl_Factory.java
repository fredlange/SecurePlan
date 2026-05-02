package se.secureplan.app.core.data.repository.impl;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import se.secureplan.app.core.data.local.dao.ProjectDao;

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
public final class ProjectRepositoryImpl_Factory implements Factory<ProjectRepositoryImpl> {
  private final Provider<ProjectDao> daoProvider;

  public ProjectRepositoryImpl_Factory(Provider<ProjectDao> daoProvider) {
    this.daoProvider = daoProvider;
  }

  @Override
  public ProjectRepositoryImpl get() {
    return newInstance(daoProvider.get());
  }

  public static ProjectRepositoryImpl_Factory create(Provider<ProjectDao> daoProvider) {
    return new ProjectRepositoryImpl_Factory(daoProvider);
  }

  public static ProjectRepositoryImpl newInstance(ProjectDao dao) {
    return new ProjectRepositoryImpl(dao);
  }
}
