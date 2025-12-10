package dev.machado001.apptemplate.core.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import dev.machado001.apptemplate.data.local.db.TemplateDatabase
import dev.machado001.apptemplate.data.local.preferences.TemplatePreferencesDataSource
import dev.machado001.apptemplate.data.repository.TemplateRepositoryImpl
import dev.machado001.apptemplate.domain.repository.TemplateRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            TemplateDatabase::class.java,
            "template.db"
        ).fallbackToDestructiveMigration().build()
    }

    single { get<TemplateDatabase>().templateDao() }

    single<DataStore<Preferences>> {
        PreferenceDataStoreFactory.create(
            scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
        ) {
            androidContext().preferencesDataStoreFile("template_preferences")
        }
    }

    single { TemplatePreferencesDataSource(get()) }
    single<TemplateRepository> { TemplateRepositoryImpl(get(), get(), get()) }
}
