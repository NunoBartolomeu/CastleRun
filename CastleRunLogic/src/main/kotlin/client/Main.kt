package org.example.client

import org.example.logic.boardFromFile
import java.io.File

fun main() {
    val boardFile = File("src/main/resources/boards/Board1.txt")
    println(boardFile.absolutePath)
    val board = boardFromFile(boardFile)
    board.print()
}