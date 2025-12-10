package dev.machado001.apptemplate.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.machado001.loggur.Loggur
import dev.machado001.apptemplate.common.TAG
import dev.machado001.apptemplate.domain.model.TemplateItem
import dev.machado001.apptemplate.domain.usecase.GetTemplateItemsUseCase
import dev.machado001.apptemplate.domain.usecase.SeedTemplateDataUseCase
import dev.machado001.apptemplate.domain.usecase.UpsertTemplateItemUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class HomeUiState(
    val items: List<TemplateItem> = emptyList(),
    val isLoading: Boolean = true
)

class HomeViewModel(
    getTemplateItemsUseCase: GetTemplateItemsUseCase,
    private val upsertTemplateItemUseCase: UpsertTemplateItemUseCase,
    private val seedTemplateDataUseCase: SeedTemplateDataUseCase,
    private val loggur: Loggur
) : ViewModel() {

    private val tag = TAG

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()
    private val eventsChannel = Channel<HomeEvent>(Channel.BUFFERED)
    val events = eventsChannel.receiveAsFlow()

    init {
        viewModelScope.launch { seedTemplateDataUseCase() }

        viewModelScope.launch {
            getTemplateItemsUseCase().collect { items ->
                _uiState.update { state ->
                    state.copy(items = items, isLoading = false)
                }
            }
        }
    }

    fun onAction(action: HomeAction) {
        when (action) {
            HomeAction.OnAddPlaceholder -> addPlaceholderItem()
            HomeAction.OnNavigateToSettings -> emitEvent(HomeEvent.NavigateToSettings)
        }
    }

    private fun addPlaceholderItem() = viewModelScope.launch {
        val count = _uiState.value.items.size + 1
        val item = TemplateItem(
            title = "Composable $count",
            description = "Replace or extend this placeholder to fit your feature."
        )
        upsertTemplateItemUseCase(item)
        loggur.debug(tag, "Added placeholder item #$count")
    }

    private fun emitEvent(event: HomeEvent) = viewModelScope.launch {
        eventsChannel.send(event)
    }
}
