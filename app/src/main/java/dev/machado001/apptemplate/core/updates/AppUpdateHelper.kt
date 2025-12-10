package dev.machado001.apptemplate.core.updates

import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.install.model.UpdateAvailability
import com.machado001.loggur.Loggur
import dev.machado001.apptemplate.common.TAG
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

sealed interface AppUpdateStatus {
    data class UpdateAvailable(val info: AppUpdateInfo) : AppUpdateStatus
    data object UpToDate : AppUpdateStatus
    data class Failed(val reason: String?) : AppUpdateStatus
}

class AppUpdateHelper(
    private val appUpdateManager: AppUpdateManager,
    private val loggur: Loggur
) {
    private val tag = TAG

    suspend fun checkForUpdates(): AppUpdateStatus = suspendCancellableCoroutine { continuation ->
        appUpdateManager.appUpdateInfo
            .addOnSuccessListener { info ->
                if (info.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {
                    continuation.resume(AppUpdateStatus.UpdateAvailable(info))
                } else {
                    continuation.resume(AppUpdateStatus.UpToDate)
                }
            }
            .addOnFailureListener { throwable ->
                loggur.warn(tag, "Failed to fetch update info: ${throwable.message}")
                continuation.resume(AppUpdateStatus.Failed(throwable.message))
            }
    }

    fun completeUpdate() {
        appUpdateManager.completeUpdate()
    }
}
