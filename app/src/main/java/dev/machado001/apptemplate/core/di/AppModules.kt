package dev.machado001.apptemplate.core.di

import org.koin.core.module.Module

val appModules: List<Module> = listOf(
    coreModule,
    dataModule,
    domainModule,
    viewModelModule
)
