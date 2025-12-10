package com.machado001.loggur

import android.util.Log

/**
 * Default Android-backed logger.
 */
class AndroidLoggur(private val mainTag: String) : Loggur {
    override fun debug(tag: String, message: String) {
        Log.d("[$mainTag][$tag]", message)
    }

    override fun verbose(tag: String, message: String) {
        Log.v("[$mainTag][$tag]", message)
    }

    override fun info(tag: String, message: String) {
        Log.i("[$mainTag][$tag]", message)
    }

    override fun warn(tag: String, message: String) {
        Log.w("[$mainTag][$tag]", message)
    }

    override fun error(tag: String, message: String) {
        Log.e("[$mainTag][$tag]", message)
    }
}
