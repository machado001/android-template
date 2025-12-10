package dev.machado001.apptemplate.domain.usecase

import dev.machado001.apptemplate.domain.repository.TemplateRepository

class SeedTemplateDataUseCase(
    private val repository: TemplateRepository
) {
    suspend operator fun invoke() {
        repository.seedDefaultsIfEmpty()
    }
}
