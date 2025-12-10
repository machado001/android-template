package dev.machado001.apptemplate.core.di

import dev.machado001.apptemplate.presentation.screens.home.HomeViewModel
import dev.machado001.apptemplate.presentation.screens.settings.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel(get(), get(), get(), get()) }
    viewModel { SettingsViewModel(get(), get(), get()) }
}
