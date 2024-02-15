package org.example.logic

import org.example.models.board.Board
import org.example.models.board.Tile
import java.io.File

fun boardFromFile(file: File): Board<Tile> {
    if (!file.isFile)
        throw IllegalArgumentException("Path needs to be a file")
    if (file.extension == ".txt")
        throw IllegalArgumentException("File needs to end in \'.txt\'")

    val json = file.readText()
    return json.toObject()
}

fun boardToFile(board: Board<Tile>, file: File) {
    val json = board.toJson()
    file.writeText(json)
}