package dev.machado001.apptemplate.presentation.screens.settings

sealed interface SettingsEvent {
    data object NavigateBack : SettingsEvent
}
