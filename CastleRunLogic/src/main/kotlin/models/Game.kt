package org.example.models

import utility.random
import org.example.models.board.Board
import org.example.models.board.Position
import org.example.models.player.Piece
import org.example.models.player.Player
import models.variables.GameVariables
import models.variables.ItemVariables
import org.example.logger.RGB
import org.example.logic.GameLogic
import org.example.models.turn.*

data class Game(
    val id: String,
    val board: Board,
    val players: List<Player>,
    val gameVariables: GameVariables = GameVariables(),
    val itemVariables: ItemVariables = ItemVariables(),
    val dices: List<Dice>,
) { 
    var currRound = Round(0, mutableListOf())
    var currTurn = Turn(0, "", emptyList())

    fun getPlayer(username: String): Player = players.first { it.username == username }
    fun getPieceAt(position: Position): Piece? = players.flatMap { it.pieces }.find { it.position == position }
    fun getActivePieces(player: Player): List<Piece> = player.pieces.filter { !board.getExits().contains(it.position) }
}