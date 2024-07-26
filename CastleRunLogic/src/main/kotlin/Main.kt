package org.example

import org.example.models.Game
import org.example.models.Printer
import org.example.models.board.Position
import org.example.models.player.Piece
import org.example.models.player.Player
import models.variables.GameVariables
import org.example.logger.ConsoleLogger
import org.example.logger.RGB
import org.example.logic.GameLogic
import org.example.logic.startGame
import org.example.models.board.Dir
import org.example.models.turn.Dice
import utility.boardFromFile
import java.io.File

fun main() {
    val boardFile = File("src/main/resources/boards/Board1.txt")
    println(boardFile.absolutePath)
    val board = boardFromFile(boardFile)
    val p1 = Player("Player 1", RGB.Red)
    p1.pieces.add(Piece(p1.username, Position(2, 5), Piece.Type.Pesant))
    p1.pieces.add(Piece(p1.username, Position(2, 3), Piece.Type.Pesant))
    val p2 = Player("Player 2", RGB.Blue)
    p2.pieces.add(Piece(p2.username, Position(5, 3), Piece.Type.Pesant))
    p2.pieces.add(Piece(p2.username, Position(4, 7), Piece.Type.Pesant))
    val p3 = Player("Player 3", RGB.Brown)
    p3.pieces.add(Piece(p3.username, Position(2, 15), Piece.Type.Pesant))
    val p4 = Player("Player 4", RGB.DarkBlue)


    val players = listOf(p1, p2, p3, p4)
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

    val gameLogic = GameLogic(game, ConsoleLogger())

    gameLogic.startGame()

    val start = Position(5, 15)
    val maxDistance = 10

    //Printer.printLayout(board)
    println("\n")
    Printer.printPathfinder(board, start, maxDistance, listOf(Dir.E), 5, false, false)
    println("\n")
    //Printer.printGame(game)
}
