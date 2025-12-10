package dev.machado001.apptemplate.core.di

import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.review.ReviewManagerFactory
import com.machado001.loggur.AndroidLoggur
import com.machado001.loggur.Loggur
import dev.machado001.apptemplate.core.updates.AppUpdateHelper
import dev.machado001.apptemplate.core.updates.InAppReviewHelper
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val coreModule = module {
    single<Loggur> { AndroidLoggur(mainTag = "AppTemplate") }

    single { ReviewManagerFactory.create(androidContext()) }
    single { AppUpdateManagerFactory.create(androidContext()) }
    single { InAppReviewHelper(get(), get()) }
    single { AppUpdateHelper(get(), get()) }
}
