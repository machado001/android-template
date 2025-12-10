package dev.machado001.apptemplate.presentation.screens.home

sealed interface HomeEvent {
    data object NavigateToSettings : HomeEvent
}
