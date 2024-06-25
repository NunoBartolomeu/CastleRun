package org.example

import org.example.models.Game
import org.example.models.Printer
import org.example.models.board.Position
import org.example.models.player.Piece
import org.example.models.player.Player
import models.variables.GameVariables
import org.example.logic.GameLogic
import org.example.models.board.Dir
import org.example.models.turn.Dice
import utility.boardFromFile
import java.io.File

fun mainn() {
    val start = Position(7, 12)
    val maxDistance = 300

    val boardFile = File("src/main/resources/boards/Board2.txt")
    val board = boardFromFile(boardFile)

    Printer.printLayout(board)
    println("\n")
   // Printer.printPathfinder(board, start, maxDistance, 15, false, false, false)
}

fun main() {
    val boardFile = File("src/main/resources/boards/Board1.txt")
    println(boardFile.absolutePath)
    val board = boardFromFile(boardFile)
    val p1 = Player("Player 1")
    p1.pieces.add(Piece(p1.username, Position(2, 5), 1))
    p1.pieces.add(Piece(p1.username, Position(2, 3), 1))
    val p2 = Player("Player 2")
    p2.pieces.add(Piece(p2.username, Position(5, 3), 1))
    p2.pieces.add(Piece(p2.username, Position(4, 7), 1))
    val p3 = Player("Player 3")
    p3.pieces.add(Piece(p3.username, Position(2, 15), 1))
    val p4 = Player("Player 4")


    val players = listOf(p1, p2, p3)
    val rules = GameVariables(
        numDicesToMove = 1000
    )

    val game = Game(
        id = "TestGame",
        board = board,
        players = players,
        gameVariables = rules,
        dices = listOf(
            Dice(arrayOf(1,2,3,4,5,6)),
            Dice(arrayOf(1,2,3,4,5,6,6,6)),
            Dice(arrayOf(2,4,6,8,10,12))
        )
    )

    println(game.getCurrTurn().diceValues)
    GameLogic.startTurn(game)
    println(game.getCurrTurn().diceValues)
    GameLogic.startTurn(game)
    println(game.getCurrTurn().diceValues)
    GameLogic.startTurn(game)
    println(game.getCurrTurn().diceValues)

    val start = Position(5, 15)
    val maxDistance = 10

    //Printer.printLayout(board)
    println("\n")
    Printer.printPathfinder(board, start, maxDistance, listOf(Dir.E), 5, false, false)
    println("\n")
    //Printer.printGame(game)
}
