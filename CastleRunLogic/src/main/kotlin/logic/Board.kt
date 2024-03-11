package org.example.logic

import org.example.models.board.Board
import org.example.models.board.MutableTile
import org.example.models.board.Tile
import java.io.File

fun boardFromFile(file: File): Board<Tile> {
    if (!file.isFile)
        throw IllegalArgumentException("Path needs to be a file")
    if (file.extension == ".txt")
        throw IllegalArgumentException("File needs to end in \'.txt\'")

    val layout = file.readText()
    return boardFromLayout(layout)
}

fun boardFromLayout(layout: String): Board<Tile> {
    val lines = layout.split("\r\n")
    val tiles = Array(lines.size) { row ->
        Array(lines[0].length) { col ->
            Tile(row, col, Tile.Type.entries[lines[row][col].toString().toInt()])
        }
    }
    return Board(tiles)
}

fun boardToFile(board: Board<Tile>, file: File) {
    val json = board.toJson()
    file.writeText(json)
}