package dev.machado001.apptemplate.presentation.screens.settings

import android.app.Activity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import dev.machado001.apptemplate.core.updates.AppUpdateStatus
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsRoute(
    onBack: () -> Unit,
    viewModel: SettingsViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val activity = LocalActivity.current
    LaunchedEffect(Unit) {
        viewModel.events.collectLatest { event ->
            when (event) {
                SettingsEvent.NavigateBack -> onBack()
            }
        }
    }

    SettingsScreen(
        state = state,
        onAction = { viewModel.onAction(activity, it) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    state: SettingsUiState,
    onAction: (SettingsAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = { onAction(SettingsAction.OnBack) }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text(
                text = "Review & Update helpers",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "These showcase how to wrap Play APIs behind testable helpers.",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 4.dp, bottom = 12.dp)
            )
            Button(onClick = { onAction(SettingsAction.OnRequestReview) }, modifier = Modifier.fillMaxWidth()) {
                Text("Request in-app review")
            }
            Spacer(modifier = Modifier.height(12.dp))
            Button(onClick = { onAction(SettingsAction.OnCheckForUpdates) }, modifier = Modifier.fillMaxWidth()) {
                Text("Check for app update")
            }
            Spacer(modifier = Modifier.height(12.dp))
            if (state.isCheckingUpdates) {
                CircularProgressIndicator()
            } else {
                UpdateStatus(state.updateStatus)
            }
            Spacer(modifier = Modifier.height(12.dp))
            state.reviewCompleted?.let { success ->
                val text = if (success) "Review flow completed" else "Review flow not available"
                Text(text, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Composable
private fun UpdateStatus(status: AppUpdateStatus?) {
    val message = when (status) {
        is AppUpdateStatus.UpdateAvailable -> "Update available: ${status.info.availableVersionCode()}"
        AppUpdateStatus.UpToDate -> "App is up to date"
        is AppUpdateStatus.Failed -> "Could not check for updates: ${status.reason ?: "Unknown"}"
        null -> "Tap the button to check for updates"
    }
    Text(text = message, style = MaterialTheme.typography.bodySmall)
}
