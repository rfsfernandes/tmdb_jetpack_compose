package xyz.rfsfernandes.tmdbdemo.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import xyz.rfsfernandes.tmdbdemo.presentation.navigation.routes.CoreNavigationRoutes
import xyz.rfsfernandes.tmdbdemo.presentation.ui.details.DetailsScreen
import xyz.rfsfernandes.tmdbdemo.presentation.ui.home.HomeScreen

@Composable
fun NavigationGraph() {
    val navigationController = rememberNavController()

    NavHost(
        navController = navigationController,
        startDestination = CoreNavigationRoutes.HomeScreen.route,
    ) {
        composable(CoreNavigationRoutes.HomeScreen.route) {
            HomeScreen(
                onMovieClick = { movieId ->
                    navigationController.navigate(
                        CoreNavigationRoutes.MovieDetailsScreen.createRoute(
                            movieId
                        )
                    )
                }
            )
        }

        composable(
            route = CoreNavigationRoutes.MovieDetailsScreen.route,
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getInt("movieId") ?: 0
            DetailsScreen(movieId = movieId, onBackClick = { navigationController.popBackStack() })
        }
    }
}
