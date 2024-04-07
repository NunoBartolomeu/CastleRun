package org.example.client

import org.example.logic.boardFromFile
import org.example.models.Game
import org.example.models.board.position.Position
import org.example.models.player.Piece
import org.example.models.player.Player
import org.example.models.printGame
import org.example.models.rules.GameRules
import java.io.File

fun main() {
    val boardFile = File("src/main/resources/boards/Board2.txt")
    println(boardFile.absolutePath)
    val board = boardFromFile(boardFile)
    val p1 = Player("Player 1")
    p1.pieces.add(Piece(p1.username, Position(2, 5)))
    p1.pieces.add(Piece(p1.username, Position(2, 3)))
    val p2 = Player("Player 2")
    p2.pieces.add(Piece(p2.username, Position(5, 3)))
    p2.pieces.add(Piece(p2.username, Position(4, 7)))
    val p3 = Player("Player 3")
    p3.pieces.add(Piece(p3.username, Position(2, 15)))
    val p4 = Player("Player 4")
    
    
    val players = listOf(p1, p2, p3)
    val rules = GameRules(
        numDicesToMove = 1000
    )
    
    val game = Game(
        id = "TestGame",
        board = board,
        players = players,
        rules = rules
    )

    printGame(game)
    println(game.board.entries)
    game.deploy(p1, Position(2, 2), Position(3,4), 5)
    printGame(game)
}