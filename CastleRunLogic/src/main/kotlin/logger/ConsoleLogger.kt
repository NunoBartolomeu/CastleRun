package org.example.logger

class ConsoleLogger : Logger {
    override fun log(entity: String, message: String) {
        println("{$entity}: $message")
    }

    override fun log(entity: String, message: String, color: RGB) {
        val reset = "\u001B[0m"
        println("${color}{$entity}: $message$reset")
    }
}
