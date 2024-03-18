package org.example.client

import org.example.logic.boardFromFile
import java.io.File

fun main() {
    val boardFile = File("src/main/resources/boards/Board1.txt")
    println(boardFile.absolutePath)
    val board = boardFromFile(boardFile)
    board.print()
    val p1 = Player("Player 1")
    val p2 = Player("Player 2")
    val players = listOf(p1, p2)
    val rules = GameRules()
    
    val game = Game(board, players, rules)
}