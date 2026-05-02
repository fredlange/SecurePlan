package se.secureplan.app.core.data.repository.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import se.secureplan.app.core.data.local.dao.ProjectDao
import se.secureplan.app.core.data.mapper.toDomain
import se.secureplan.app.core.data.mapper.toEntity
import se.secureplan.app.core.domain.model.Project
import se.secureplan.app.core.domain.model.SystemCategory
import se.secureplan.app.core.domain.repository.ProjectRepository
import javax.inject.Inject

class ProjectRepositoryImpl @Inject constructor(
    private val dao: ProjectDao
) : ProjectRepository {

    override fun getAllProjects(): Flow<List<Project>> =
        dao.getAllProjects().map { list -> list.map { it.toDomain() } }

    override fun getProjectsByCategory(category: SystemCategory): Flow<List<Project>> =
        dao.getProjectsByCategory(category.name).map { list -> list.map { it.toDomain() } }

    override fun getProjectById(id: String): Flow<Project?> =
        dao.getProjectById(id).map { it?.toDomain() }

    override fun searchProjects(query: String): Flow<List<Project>> =
        dao.searchProjects(query).map { list -> list.map { it.toDomain() } }

    override suspend fun saveProject(project: Project) {
        dao.insertProject(project.toEntity())
    }

    override suspend fun deleteProject(id: String) {
        dao.deleteProjectById(id)
    }

    override fun getProjectCount(): Flow<Int> = dao.getProjectCount()
}
