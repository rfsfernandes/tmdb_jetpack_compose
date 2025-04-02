package xyz.rfsfernandes.tmdbdemo.presentation.navigation.routes

sealed class CoreNavigationRoutes(val route: String) {
    data object HomeScreen: CoreNavigationRoutes(route = "home_screen")
}
