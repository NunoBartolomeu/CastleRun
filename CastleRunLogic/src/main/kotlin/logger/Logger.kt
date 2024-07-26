package org.example.logger

interface Logger {
    fun log(entity: String, message: String)
    fun log(entity: String, message: String, color: RGB)
}
