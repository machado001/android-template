package com.machado001.loggur

/**
 * Abstraction over logging so production and tests can swap implementations.
 */
interface Loggur {
    fun debug(tag: String, message: String)
    fun verbose(tag: String, message: String)
    fun info(tag: String, message: String)
    fun warn(tag: String, message: String)
    fun error(tag: String, message: String)
}
