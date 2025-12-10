package dev.machado001.apptemplate.presentation.screens.home

sealed interface HomeAction {
    data object OnAddPlaceholder : HomeAction
    data object OnNavigateToSettings : HomeAction
}
