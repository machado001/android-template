package dev.machado001.apptemplate.presentation.navigation

import androidx.navigation.NavHostController

class AppActions(navController: NavHostController) {
    val openSettings: () -> Unit = {
        navController.navigate(AppDestinations.SETTINGS)
    }
    val navigateBack: () -> Unit = {
        navController.popBackStack()
    }
}
