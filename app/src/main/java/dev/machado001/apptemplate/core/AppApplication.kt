package dev.machado001.apptemplate.core

import android.app.Application
import com.google.firebase.Firebase
import com.google.firebase.appcheck.appCheck
import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory
import com.google.firebase.initialize
import dev.machado001.apptemplate.core.di.appModules
import dev.machado001.apptemplate.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


/**
 * TODO: Uncomment this when firebase (analytics + crashlytics + app check) is enabled on console
 */
class AppApplication : Application() {
    override fun onCreate() {
        super.onCreate()
//        Firebase.initialize(this)
//
//        val appCheckProvider = if (BuildConfig.DEBUG) {
//            DebugAppCheckProviderFactory.getInstance()
//        } else {
//            PlayIntegrityAppCheckProviderFactory.getInstance()
//        }
//        Firebase.appCheck.installAppCheckProviderFactory(appCheckProvider)

        startKoin {
            androidContext(this@AppApplication)
            modules(appModules)
        }
    }
}
