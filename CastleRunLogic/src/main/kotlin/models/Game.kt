package org.example.models

import org.example.models.board.Board
import org.example.models.board.Tile
import org.example.models.board.position.Position
import org.example.models.player.Piece
import org.example.models.player.Player
import org.example.models.turn.Dice
import org.example.models.turn.RollDiceAction
import org.example.models.turn.Turn

data class Game(val board: Board<Tile>, val players: List<Player>, val rules: GameRules, val turns: MutableList<Turn> = mutableListOf()) {
    val currTurn: Turn get() = turns.last()
    var currPlayer: String = players[0].username
    var state: State = State.Roll

    enum class State { Roll, MoveOrDeploy, DuelRoll, DuelSelectAlly, DuelSelectEnemy, EndTurn }

    

    fun rollDices(player: String) {
        if (player != currPlayer) throw Exception("It's not your turn")
        if (state != State.Roll && state != State.DuelRoll) throw Exception("You can't roll the dices right now")
        if (currTurn.dices.isNotEmpty()) throw Exception("You already rolled the dices")

        val dice = Dice(player, rules.numDicesToMove, false)
        currTurn.dices.add(dice)
        currTurn.actions.add(RollDiceAction(player, dice))

        state = State.MoveOrDeploy
    }

    fun move(player: String, from: Position, to: Position) {
        if (player != currPlayer) throw Exception("It's not your turn")
        if (state != State.MoveOrDeploy) throw Exception("You can't move a piece right now")
        if (currTurn.dices.isEmpty()) throw Exception("You need to roll the dices first")

        val piece = getPieceAt(from) ?: throw Exception("There's no piece in the position $from")
        if (piece.player != player) throw Exception("You can't move the enemy's piece")

        val dice = currTurn.dices.last()
        val distance = from.distance(to)
        if (dice.used.all { it }) throw Exception("You already used all the dices")
        if (distance !in dice.values) throw Exception("You can't move the piece $distance tiles")

        board.movePiece(from, to)
        currTurn.actions.add(RollDiceAction(player, dice))
        dice.use(distance)
    }

    private fun getPieceAt(position: Position): Piece? {
        players.forEach { player ->
            player.getPieceAt(position) ?: return@forEach
        }
        return null
    }
}

    /** Move:
     * 1. The current player rolls the current turn dices
     * 2. The player will move or deploy a piece by each value of the dices
     */


    /** Duel:
     * 1. If the move dices are the same, the player will start a duel as long as there are enemy pieces in the board
     * 2. The system chooses a random reward and punishment for the duel
     * 3. The player chooses an ally piece and an enemy piece to duel
     * 4. Both players in the duel will roll the duel dices
     * 5. The current player only needs a number equal or greater than the enemy player to win the duel
     * 6. The winner gets the reward and the loser gets the punishment
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
