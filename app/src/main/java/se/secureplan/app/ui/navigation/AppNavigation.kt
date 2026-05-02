package se.secureplan.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import se.secureplan.app.feature.calculations.CalculationsScreen
import se.secureplan.app.feature.components.ComponentSummaryScreen
import se.secureplan.app.feature.dashboard.DashboardScreen
import se.secureplan.app.feature.drawing.DrawingScreen
import se.secureplan.app.feature.export.ExportScreen
import se.secureplan.app.feature.files.ProjectFileScreen
import se.secureplan.app.feature.photos.GeoPhotoDetailScreen
import se.secureplan.app.feature.photos.GeoPhotoScreen
import se.secureplan.app.feature.products.AddEditProductScreen
import se.secureplan.app.feature.products.ProductDetailScreen
import se.secureplan.app.feature.products.ProductLibraryScreen
import se.secureplan.app.feature.projects.ProjectsScreen
import se.secureplan.app.feature.protocols.ProtocolFormScreen
import se.secureplan.app.feature.protocols.ProtocolsScreen
import se.secureplan.app.feature.settings.SettingsScreen

object Routes {
    const val PROJECTS             = "projects"
    const val DASHBOARD            = "dashboard/{projectId}"
    const val DRAWING              = "drawing/{drawingId}"
    const val PRODUCTS             = "products"
    const val PRODUCTS_ADD         = "products/add"
    const val PRODUCT_DETAIL       = "products/{productId}"
    const val PRODUCT_EDIT         = "products/{productId}/edit"
    const val PROJECT_PHOTOS       = "project/{projectId}/photos"
    const val PHOTO_DETAIL         = "photo/{photoId}"
    const val PROJECT_CALCULATIONS = "project/{projectId}/calculations"
    const val PROJECT_COMPONENTS   = "project/{projectId}/components"
    const val PROJECT_PROTOCOLS    = "project/{projectId}/protocols"
    const val PROTOCOL_FORM        = "project/{projectId}/protocol/form?templateId={templateId}&instanceId={instanceId}"
    const val PROJECT_EXPORT       = "project/{projectId}/export"
    const val PROJECT_FILES        = "project/{projectId}/files"
    const val SETTINGS             = "settings"

    fun dashboard(projectId: String)           = "dashboard/$projectId"
    fun drawing(drawingId: String)             = "drawing/$drawingId"
    fun productDetail(productId: String)       = "products/$productId"
    fun productEdit(productId: String)         = "products/$productId/edit"
    fun projectPhotos(projectId: String)       = "project/$projectId/photos"
    fun photoDetail(photoId: String)           = "photo/$photoId"
    fun projectCalculations(projectId: String) = "project/$projectId/calculations"
    fun projectComponents(projectId: String)   = "project/$projectId/components"
    fun projectProtocols(projectId: String)    = "project/$projectId/protocols"
    fun protocolForm(projectId: String, templateId: String? = null, instanceId: String? = null): String {
        val t = templateId?.let { "templateId=$it" } ?: "templateId="
        val i = instanceId?.let { "instanceId=$it" } ?: "instanceId="
        return "project/$projectId/protocol/form?$t&$i"
    }
    fun projectExport(projectId: String)       = "project/$projectId/export"
    fun projectFiles(projectId: String)        = "project/$projectId/files"
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.PROJECTS
    ) {
        // ── Existing routes ───────────────────────────────────────────────────

        composable(Routes.PROJECTS) {
            ProjectsScreen(
                onProjectClick = { projectId ->
                    navController.navigate(Routes.dashboard(projectId))
                },
                onSettingsClick = { navController.navigate(Routes.SETTINGS) }
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
                onBack              = { navController.popBackStack() },
                onComponentsClick   = { navController.navigate(Routes.projectComponents(projectId)) },
                onCalculationsClick = { navController.navigate(Routes.projectCalculations(projectId)) },
                onPhotosClick       = { navController.navigate(Routes.projectPhotos(projectId)) },
                onProductsClick     = { navController.navigate(Routes.PRODUCTS) },
                onProtocolsClick    = { navController.navigate(Routes.projectProtocols(projectId)) },
                onExportClick       = { navController.navigate(Routes.projectExport(projectId)) },
                onFilesClick        = { navController.navigate(Routes.projectFiles(projectId)) }
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

        // ── Product Library ───────────────────────────────────────────────────

        composable(Routes.PRODUCTS) {
            ProductLibraryScreen(
                onBack         = { navController.popBackStack() },
                onProductClick = { productId -> navController.navigate(Routes.productDetail(productId)) },
                onAddProduct   = { navController.navigate(Routes.PRODUCTS_ADD) }
            )
        }

        composable(Routes.PRODUCTS_ADD) {
            AddEditProductScreen(
                productId = null,
                onSaved   = { navController.popBackStack() },
                onBack    = { navController.popBackStack() }
            )
        }

        composable(
            route = Routes.PRODUCT_DETAIL,
            arguments = listOf(navArgument("productId") { type = NavType.StringType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId") ?: return@composable
            ProductDetailScreen(
                productId = productId,
                onBack    = { navController.popBackStack() },
                onEdit    = { navController.navigate(Routes.productEdit(productId)) }
            )
        }

        composable(
            route = Routes.PRODUCT_EDIT,
            arguments = listOf(navArgument("productId") { type = NavType.StringType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId") ?: return@composable
            AddEditProductScreen(
                productId = productId,
                onSaved   = { navController.popBackStack() },
                onBack    = { navController.popBackStack() }
            )
        }

        // ── Geotagged Photos ─────────────────────────────────────────────────

        composable(
            route = Routes.PROJECT_PHOTOS,
            arguments = listOf(navArgument("projectId") { type = NavType.StringType })
        ) { backStackEntry ->
            val projectId = backStackEntry.arguments?.getString("projectId") ?: return@composable
            GeoPhotoScreen(
                projectId    = projectId,
                onBack       = { navController.popBackStack() },
                onPhotoClick = { photoId -> navController.navigate(Routes.photoDetail(photoId)) }
            )
        }

        composable(
            route = Routes.PHOTO_DETAIL,
            arguments = listOf(navArgument("photoId") { type = NavType.StringType })
        ) { backStackEntry ->
            val photoId = backStackEntry.arguments?.getString("photoId") ?: return@composable
            GeoPhotoDetailScreen(
                photoId = photoId,
                onBack  = { navController.popBackStack() }
            )
        }

        // ── Calculations ──────────────────────────────────────────────────────

        composable(
            route = Routes.PROJECT_CALCULATIONS,
            arguments = listOf(navArgument("projectId") { type = NavType.StringType })
        ) { backStackEntry ->
            val projectId = backStackEntry.arguments?.getString("projectId") ?: return@composable
            CalculationsScreen(
                projectId = projectId,
                onBack    = { navController.popBackStack() }
            )
        }

        // ── Component Summary ─────────────────────────────────────────────────

        composable(
            route = Routes.PROJECT_COMPONENTS,
            arguments = listOf(navArgument("projectId") { type = NavType.StringType })
        ) { backStackEntry ->
            val projectId = backStackEntry.arguments?.getString("projectId") ?: return@composable
            ComponentSummaryScreen(
                projectId = projectId,
                onBack    = { navController.popBackStack() }
            )
        }

        // ── Protocols ─────────────────────────────────────────────────────────

        composable(
            route = Routes.PROJECT_PROTOCOLS,
            arguments = listOf(navArgument("projectId") { type = NavType.StringType })
        ) { backStackEntry ->
            val projectId = backStackEntry.arguments?.getString("projectId") ?: return@composable
            ProtocolsScreen(
                projectId  = projectId,
                onBack     = { navController.popBackStack() },
                onOpenForm = { templateId, instanceId ->
                    navController.navigate(Routes.protocolForm(projectId, templateId, instanceId))
                }
            )
        }

        composable(
            route = Routes.PROTOCOL_FORM,
            arguments = listOf(
                navArgument("projectId")  { type = NavType.StringType },
                navArgument("templateId") { type = NavType.StringType; defaultValue = "" },
                navArgument("instanceId") { type = NavType.StringType; defaultValue = "" }
            )
        ) { backStackEntry ->
            val projectId  = backStackEntry.arguments?.getString("projectId")  ?: return@composable
            val templateId = backStackEntry.arguments?.getString("templateId")?.takeIf { it.isNotEmpty() }
            val instanceId = backStackEntry.arguments?.getString("instanceId")?.takeIf { it.isNotEmpty() }
            ProtocolFormScreen(
                templateId = templateId,
                projectId  = projectId,
                instanceId = instanceId,
                onBack     = { navController.popBackStack() }
            )
        }

        // ── Export ────────────────────────────────────────────────────────────

        composable(
            route = Routes.PROJECT_EXPORT,
            arguments = listOf(navArgument("projectId") { type = NavType.StringType })
        ) { backStackEntry ->
            val projectId = backStackEntry.arguments?.getString("projectId") ?: return@composable
            ExportScreen(
                projectId = projectId,
                onBack    = { navController.popBackStack() }
            )
        }

        // ── Settings ──────────────────────────────────────────────────────────

        composable(Routes.SETTINGS) {
            SettingsScreen(
                onBack = { navController.popBackStack() }
            )
        }

        // ── Project Files ─────────────────────────────────────────────────────

        composable(
            route = Routes.PROJECT_FILES,
            arguments = listOf(navArgument("projectId") { type = NavType.StringType })
        ) { backStackEntry ->
            val projectId = backStackEntry.arguments?.getString("projectId") ?: return@composable
            ProjectFileScreen(
                projectId = projectId,
                onBack    = { navController.popBackStack() }
            )
        }
    }
}
