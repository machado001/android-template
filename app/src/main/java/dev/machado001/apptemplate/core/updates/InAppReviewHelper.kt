package dev.machado001.apptemplate.core.updates

import android.app.Activity
import com.google.android.play.core.review.ReviewManager
import com.google.android.play.core.review.ReviewManagerFactory
import com.google.android.gms.tasks.Task
import com.machado001.loggur.Loggur
import dev.machado001.apptemplate.common.TAG
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class InAppReviewHelper(
    private val reviewManager: ReviewManager,
    private val loggur: Loggur
) {
    private val tag = TAG

    suspend fun requestReview(activity: Activity): Boolean =
        suspendCancellableCoroutine { continuation ->
            reviewManager
                .requestReviewFlow()
                .addOnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        logFailure("requestFlow", task)
                        continuation.resume(false)
                        return@addOnCompleteListener
                    }

                    val reviewInfo = task.result
                    reviewManager.launchReviewFlow(activity, reviewInfo)
                        .addOnCompleteListener { launchTask ->
                            if (!launchTask.isSuccessful) {
                                logFailure("launchFlow", launchTask)
                            }
                            continuation.resume(launchTask.isSuccessful)
                        }
                        .addOnFailureListener { throwable ->
                            continuation.resumeWithException(throwable)
                        }
                }
                .addOnFailureListener { throwable ->
                    loggur.error(tag, "Failure starting review flow ${throwable.message}")
                    continuation.resume(false)
                }
        }

    private fun logFailure(stage: String, task: Task<*>) {
        val code = task.exception?.message ?: "unknown"
        loggur.warn(tag, "Review $stage failed with $code")
    }

    companion object {
        fun build(context: android.content.Context, loggur: Loggur): InAppReviewHelper {
            val manager = ReviewManagerFactory.create(context)
            return InAppReviewHelper(manager, loggur)
        }
    }
}
