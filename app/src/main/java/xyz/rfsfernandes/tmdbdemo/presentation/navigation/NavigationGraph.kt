package xyz.rfsfernandes.tmdbdemo.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import xyz.rfsfernandes.tmdbdemo.presentation.navigation.routes.CoreNavigationRoutes
import xyz.rfsfernandes.tmdbdemo.presentation.ui.home.HomeScreen

@Composable
fun NavigationGraph() {
    val navigationController = rememberNavController()
//    val currentDestination by navigationController.currentBackStackEntryAsState()

    NavHost(
        navController = navigationController,
        startDestination = CoreNavigationRoutes.HomeScreen.route,
    ) {
        composable(CoreNavigationRoutes.HomeScreen.route) { HomeScreen() }
    }
}
