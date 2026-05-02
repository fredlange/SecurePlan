package se.secureplan.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import se.secureplan.app.feature.dashboard.DashboardScreen
import se.secureplan.app.feature.drawing.DrawingScreen
import se.secureplan.app.feature.projects.ProjectsScreen

object Routes {
    const val PROJECTS   = "projects"
    const val DASHBOARD  = "dashboard/{projectId}"
    const val DRAWING    = "drawing/{drawingId}"

    fun dashboard(projectId: String) = "dashboard/$projectId"
    fun drawing(drawingId: String)   = "drawing/$drawingId"
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.PROJECTS
    ) {
        composable(Routes.PROJECTS) {
            ProjectsScreen(
                onProjectClick = { projectId ->
                    navController.navigate(Routes.dashboard(projectId))
                }
            )
        }

        composable(
            route = Routes.DASHBOARD,
            arguments = listOf(navArgument("projectId") { type = NavType.StringType })
        ) { backStackEntry ->
            val projectId = backStackEntry.arguments?.getString("projectId") ?: return@composable
            DashboardScreen(
                projectId = projectId,
                onDrawingClick = { drawingId ->
                    navController.navigate(Routes.drawing(drawingId))
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Routes.DRAWING,
            arguments = listOf(navArgument("drawingId") { type = NavType.StringType })
        ) { backStackEntry ->
            val drawingId = backStackEntry.arguments?.getString("drawingId") ?: return@composable
            DrawingScreen(
                drawingId = drawingId,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
