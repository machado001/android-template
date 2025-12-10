package dev.machado001.apptemplate.domain.usecase

import dev.machado001.apptemplate.domain.model.TemplateItem
import dev.machado001.apptemplate.domain.repository.TemplateRepository

class UpsertTemplateItemUseCase(
    private val repository: TemplateRepository
) {
    suspend operator fun invoke(item: TemplateItem) {
        repository.upsert(item)
    }
}
