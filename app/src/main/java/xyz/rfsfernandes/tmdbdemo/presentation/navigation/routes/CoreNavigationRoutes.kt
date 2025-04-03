package xyz.rfsfernandes.tmdbdemo.presentation.navigation.routes

sealed class CoreNavigationRoutes(val route: String) {
    data object HomeScreen : CoreNavigationRoutes(route = "home_screen")
    data object MovieDetailsScreen : CoreNavigationRoutes("movie_details_screen/{movieId}") {
        fun createRoute(movieId: Int) = "movie_details_screen/$movieId"
    }
}
