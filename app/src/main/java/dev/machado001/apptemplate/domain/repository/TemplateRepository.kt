package dev.machado001.apptemplate.domain.repository

import dev.machado001.apptemplate.domain.model.TemplateItem
import kotlinx.coroutines.flow.Flow

interface TemplateRepository {
    fun observeItems(): Flow<List<TemplateItem>>
    suspend fun upsert(item: TemplateItem)
    suspend fun seedDefaultsIfEmpty()
}
