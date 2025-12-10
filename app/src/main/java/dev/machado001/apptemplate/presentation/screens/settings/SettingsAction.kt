package dev.machado001.apptemplate.presentation.screens.settings

sealed interface SettingsAction {
    data object OnBack : SettingsAction
    data object OnRequestReview : SettingsAction
    data object OnCheckForUpdates : SettingsAction
}
