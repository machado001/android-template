package dev.machado001.apptemplate.data.repository

import com.machado001.loggur.Loggur
import dev.machado001.apptemplate.data.local.db.TemplateDao
import dev.machado001.apptemplate.data.local.db.TemplateEntity
import dev.machado001.apptemplate.data.local.preferences.TemplatePreferencesDataSource
import dev.machado001.apptemplate.common.TAG
import dev.machado001.apptemplate.domain.model.TemplateItem
import dev.machado001.apptemplate.domain.repository.TemplateRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class TemplateRepositoryImpl(
    private val dao: TemplateDao,
    private val preferences: TemplatePreferencesDataSource,
    private val loggur: Loggur
) : TemplateRepository {

    private val tag = TAG

    override fun observeItems(): Flow<List<TemplateItem>> =
        dao.observeItems().map { entities -> entities.map { it.toDomain() } }

    override suspend fun upsert(item: TemplateItem) {
        dao.upsert(TemplateEntity.fromDomain(item))
    }

    override suspend fun seedDefaultsIfEmpty() {
        val alreadySeeded = preferences.hasSeededData.first()
        val hasData = dao.count() > 0
        if (alreadySeeded || hasData) return

        val defaults = listOf(
            TemplateEntity(title = "Composable Home", description = "Replace this item with your own feature entry point."),
            TemplateEntity(title = "Data Layer", description = "Wire your repositories to remote/local data sources."),
            TemplateEntity(title = "Domain Layer", description = "Model your use cases in isolated classes.")
        )
        dao.insertAll(defaults)
        preferences.markSeeded()
        loggur.info(tag, "Seeded default data set.")
    }
}
