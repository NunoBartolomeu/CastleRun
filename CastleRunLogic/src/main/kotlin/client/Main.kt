package org.example.client

import org.example.logic.boardFromFile
import org.example.models.Game
import org.example.models.GameRules
import org.example.models.board.position.Position
import org.example.models.player.Player
import java.io.File

fun main() {
    val boardFile = File("src/main/resources/boards/Board2.txt")
    println(boardFile.absolutePath)
    val board = boardFromFile(boardFile)
    val p1 = Player("Player 1")
    p1.deployPiece(Position(2, 5))
    p1.deployPiece(Position(2, 3))
    val p2 = Player("Player 2")
    p2.deployPiece(Position(5, 3))
    p2.deployPiece(Position(4, 7))
    val p3 = Player("Player 3")
    p3.deployPiece(Position(2, 15))
    val p4 = Player("Player 4")
    
    
    val players = listOf(p1, p2, p3)
    val rules = GameRules()
    
    val game = Game(
        board = board,
        players = players,
        rules = rules
    )

    game.printGame()
}