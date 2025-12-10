package dev.machado001.apptemplate.domain.usecase

import dev.machado001.apptemplate.domain.model.TemplateItem
import dev.machado001.apptemplate.domain.repository.TemplateRepository
import kotlinx.coroutines.flow.Flow

class GetTemplateItemsUseCase(
    private val repository: TemplateRepository
) {
    operator fun invoke(): Flow<List<TemplateItem>> = repository.observeItems()
}
