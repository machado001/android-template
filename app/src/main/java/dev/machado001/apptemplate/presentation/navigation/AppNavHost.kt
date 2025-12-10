package dev.machado001.apptemplate.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.machado001.apptemplate.presentation.screens.home.HomeRoute
import dev.machado001.apptemplate.presentation.screens.settings.SettingsRoute

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = AppDestinations.HOME
) {
    val actions = remember(navController) { AppActions(navController) }
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(AppDestinations.HOME) {
            HomeRoute(onNavigateToSettings = actions.openSettings)
        }
        composable(AppDestinations.SETTINGS) {
            SettingsRoute(onBack = actions.navigateBack)
        }
    }
}
