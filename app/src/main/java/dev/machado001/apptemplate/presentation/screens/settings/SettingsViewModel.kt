package dev.machado001.apptemplate.presentation.screens.settings

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.machado001.loggur.Loggur
import dev.machado001.apptemplate.common.TAG
import dev.machado001.apptemplate.core.updates.AppUpdateHelper
import dev.machado001.apptemplate.core.updates.AppUpdateStatus
import dev.machado001.apptemplate.core.updates.InAppReviewHelper
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class SettingsUiState(
    val updateStatus: AppUpdateStatus? = null,
    val reviewCompleted: Boolean? = null,
    val isCheckingUpdates: Boolean = false
)

class SettingsViewModel(
    private val reviewHelper: InAppReviewHelper,
    private val appUpdateHelper: AppUpdateHelper,
    private val loggur: Loggur
) : ViewModel() {

    private val tag = TAG

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()
    private val eventsChannel = Channel<SettingsEvent>(Channel.BUFFERED)
    val events = eventsChannel.receiveAsFlow()

    fun onAction(activity: Activity?, action: SettingsAction) {
        when (action) {
            SettingsAction.OnRequestReview -> requestInAppReview(activity)
            SettingsAction.OnCheckForUpdates -> checkForAppUpdates()
            SettingsAction.OnBack -> emitEvent(SettingsEvent.NavigateBack)
        }
    }

    private fun requestInAppReview(activity: Activity?) {
        if (activity == null) return
        viewModelScope.launch {
            val success = reviewHelper.requestReview(activity)
            loggur.info(tag, "Review request success: $success")
            _uiState.update { it.copy(reviewCompleted = success) }
        }
    }

    private fun checkForAppUpdates() {
        viewModelScope.launch {
            _uiState.update { it.copy(isCheckingUpdates = true) }
            val status = appUpdateHelper.checkForUpdates()
            _uiState.update { it.copy(updateStatus = status, isCheckingUpdates = false) }
        }
    }

    private fun emitEvent(event: SettingsEvent) = viewModelScope.launch {
        eventsChannel.send(event)
    }
}
