package dev.machado001.apptemplate.data.local.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TemplatePreferencesDataSource(
    private val dataStore: DataStore<Preferences>
) {
    private val hasSeededDataKey = booleanPreferencesKey("has_seeded_data")

    val hasSeededData: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[hasSeededDataKey] ?: false
    }

    suspend fun markSeeded() {
        dataStore.edit { preferences ->
            preferences[hasSeededDataKey] = true
        }
    }
}
