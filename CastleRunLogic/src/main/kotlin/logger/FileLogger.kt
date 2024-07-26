package org.example.logger

import java.io.File
import java.io.FileWriter
import java.io.IOException

class FileLogger(private val filePath: String) : Logger {
    private val fileWriter: FileWriter

    init {
        val file = File(filePath)
        fileWriter = FileWriter(file, true)
    }

    override fun log(entity: String, message: String) {
        try {
            fileWriter.write("{$entity}: $message" + System.lineSeparator())
            fileWriter.flush()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun log(entity: String, message: String, color: RGB) {
        try {
            val colorInfo = "RGB(${color.red}, ${color.green}, ${color.blue})"
            fileWriter.write("$colorInfo: {$entity}: $message" + System.lineSeparator())
            fileWriter.flush()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun close() {
        try {
            fileWriter.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
