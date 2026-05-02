package se.secureplan.app.core.domain.repository

import kotlinx.coroutines.flow.Flow
import se.secureplan.app.core.domain.model.Project
import se.secureplan.app.core.domain.model.SystemCategory

interface ProjectRepository {
    fun getAllProjects(): Flow<List<Project>>
    fun getProjectsByCategory(category: SystemCategory): Flow<List<Project>>
    fun getProjectById(id: String): Flow<Project?>
    fun searchProjects(query: String): Flow<List<Project>>
    suspend fun saveProject(project: Project)
    suspend fun deleteProject(id: String)
    fun getProjectCount(): Flow<Int>
}
