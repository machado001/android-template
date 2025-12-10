package dev.machado001.apptemplate.core.di

import dev.machado001.apptemplate.domain.usecase.GetTemplateItemsUseCase
import dev.machado001.apptemplate.domain.usecase.SeedTemplateDataUseCase
import dev.machado001.apptemplate.domain.usecase.UpsertTemplateItemUseCase
import org.koin.dsl.module

val domainModule = module {
    single { GetTemplateItemsUseCase(get()) }
    single { UpsertTemplateItemUseCase(get()) }
    single { SeedTemplateDataUseCase(get()) }
}
