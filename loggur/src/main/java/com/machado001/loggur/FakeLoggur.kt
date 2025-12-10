package com.machado001.loggur

/**
 * Logger implementation for tests that keeps output silent.
 */
object FakeLoggur : Loggur {
    override fun debug(tag: String, message: String) = Unit
    override fun verbose(tag: String, message: String) = Unit
    override fun info(tag: String, message: String) = Unit
    override fun warn(tag: String, message: String) = Unit
    override fun error(tag: String, message: String) = Unit
}
