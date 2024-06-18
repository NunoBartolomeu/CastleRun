package org.example.models

import utility.random
import org.example.models.board.Board
import org.example.models.board.Position
import org.example.models.player.Piece
import org.example.models.player.Player
import models.variables.GameVariables
import models.variables.ItemVariables
import org.example.models.turn.*

data class Game(
    val id: String,
    val board: Board,
    val players: List<Player>,
    val gameVariables: GameVariables = GameVariables(),
    val itemVariables: ItemVariables = ItemVariables(),
    val dices: List<Dice>,
    val turns: MutableList<Turn> = mutableListOf(),
    val challengeNumber: Int = random(1, 6),
) {
    fun getPlayer(username: String): Player = players.first { it.username == username }
    fun getCurrTurn(): Turn = turns.last()
    fun getPieceAt(position: Position): Piece? = players.flatMap { it.pieces }.find { it.position == position }
    fun getActivePieces(player: Player): List<Piece> = player.pieces.filter { !board.getExits().contains(it.position) }
}

/** Start Turn
 * 1. The system will roll the dices for the turn
 * 2. The system will award the player with one duel token for each dice value equal to the duel number
 */

/** Move and Deploy:
 * 1. The player will move or deploy a piece by each value of the dices
 */


/** Duel:
 * 1. The player will use one of their duel tokens
 * 2. The system chooses a random reward and punishment for the duel
 * 3. The player chooses an ally piece and an enemy piece to duel
 * 4. The current player only needs a number equal or greater than the enemy player to win the duel
 * 5. The winner gets the reward and the loser gets the punishment
 */

/** Item:
 * 1. If the dices are different, the player can use an item as long as it has one
 * 2. The player chooses the item and the piece to use it on
 * 3. The item will be removed from the player's inventory and the effect will be applied to the piece
 */

/** End Turn:
 * 1. The system will end the turn once the player has no more actions to do
 * 2. The system will check if the game is over
 * 3. The system will start the next turn
 **/
