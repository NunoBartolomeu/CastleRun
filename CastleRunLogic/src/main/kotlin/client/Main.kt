package org.example.client

import org.example.logic.boardFromFile
import org.example.models.Game
import org.example.models.GameRules
import org.example.models.player.Player
import java.io.File

fun main() {
    val boardFile = File("src/main/resources/boards/Board2.txt")
    println(boardFile.absolutePath)
    val board = boardFromFile(boardFile)
    board.print()
    val p1 = Player("Player 1")
    val p2 = Player("Player 2")
    val players = listOf(p1, p2)
    val rules = GameRules()
    
    val game = Game(board, players, rules)



    game.deploy(p1, at)
    game.move(p1.username, from, to)
}