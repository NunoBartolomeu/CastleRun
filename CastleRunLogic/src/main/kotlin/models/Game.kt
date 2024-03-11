package org.example.models

import org.example.models.board.Board
import org.example.models.board.Tile
import org.example.models.board.position.Position
import org.example.models.player.Piece
import org.example.models.player.Player
import org.example.models.turn.Dice
import org.example.models.turn.Turn

data class Game(val board: Board<Tile>, val players: List<Player>, val rules: GameRules, val turns: MutableList<Turn>) {
    //TODO Do the game "state machine"
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
