package org.example.models

import utility.random
import org.example.models.board.Board
import org.example.models.board.Tile
import org.example.models.board.Position
import org.example.models.player.Item
import org.example.models.player.Piece
import org.example.models.player.Player
import org.example.models.rules.GameVariables
import org.example.models.rules.ItemVariables
import org.example.models.turn.*
import java.util.*

data class Game(
    val id: String,
    val board: Board<Tile>,
    val players: List<Player>,
    val rules: GameVariables = GameVariables(),
    val itemVariables: ItemVariables = ItemVariables(),
    val turns: MutableList<Turn> = mutableListOf(),
    val challengeNumber: Int = random(1, 6),
) {
    private val currTurn: Turn get() = turns.last()
    private val isOver: Boolean get() = isGameOver()

    init {
        startNewTurn(players[0])
    }

    ////////////////////////////////////////// TURN LOGIC //////////////////////////////////////////

    private fun startNewTurn(player: Player) {
        val dices = MutableList(rules.numDicesToMove) { Dice(rules.minDiceValue, rules.maxDiceValue) }
        val number = if (turns.isEmpty()) 0 else currTurn.number + 1
        val turn = Turn(number, player.username, dices)
        turn.actions.add(StartTurnAction(player,number))
        turns.add(turn)
    }

    fun endTurn(player: Player) {
        if (currTurn.isPlayersTurn(player)) throw Exception("It's not your turn")
        currTurn.actions.add(EndTurnAction(player))

        val nextPlayer = getNextPlayer()
        startNewTurn(nextPlayer)
    }

    private fun getNextPlayer(): Player {
        val index = players.indexOfFirst { it.username == currTurn.playerUsername }
        return if (index == players.size - 1) players[0] else players[index + 1]
    }

    private fun isGameOver(): Boolean {
        return currTurn.actions.any { it is GameOverAction }
    }

    fun endGame() {
        currTurn.actions.add(GameOverAction(players.first {it.username == currTurn.playerUsername }))
    }

    ////////////////////////////////////////// PLAYER ACTIONS //////////////////////////////////////////

    /**
     * The game can't be over
     * This turn needs to belong to the player
     * The player needs to have unused dices (that match the distance)
     * The distance walked needs to match the dice distance
     * The position [entry] needs to be a valid entry
     * The player can't have more pieces than the max allowed
     * The position [to] needs to be empty or have an enemy piece
     */
    fun deploy(player: Player, entry: Position, to: Position, distance: Int) {
        if (isOver) throw Exception("The game is over")
        if (!currTurn.isPlayersTurn(player)) throw Exception("It's not ${player.username} turn, it's ${currTurn.playerUsername} turn")
        if (!currTurn.hasUnusedDiceWithValue(distance)) throw Exception("There's no unused dice with that value")
        if (player.pieces.size >= rules.maxActivePieces) throw Exception("You don't have any pieces left to deploy")
        if (!board.entries.any { it == entry }) throw Exception("Invalid entry")
        //TODO Check it's actually his entry or if the entries are shared
        if (board[to]!!.type != Tile.Type.FLOOR) throw Exception("Can't deploy a piece on a non-floor tile")
        //if (distance != board.calculateDistance(entry, to)) throw Exception("The distance walked doesn't match the real distance")

        val checkPosition = getPieceAt(to)
        checkForPiece(player, checkPosition)

        player.pieces.add(Piece(player.username, to))
        currTurn.dices.find { !it.used && it.value == distance }!!.used = true

        currTurn.actions.add(DeployAction(player, to))
    }

    /**
     * The game can't be over
     * This turn needs to belong to the player
     * The player needs to have unused dices (that match the distance)
     * The distance walked needs to match the dice distance
     * The piece needs to be at the position [from]
     * The position [to] needs to be empty or have an enemy piece
     */
    fun move(player: Player, from: Position, to: Position, distance: Int) {
        if (isOver) throw Exception("The game is over")
        if (!currTurn.isPlayersTurn(player)) throw Exception("It's not your turn")
        if (!currTurn.hasUnusedDice()) throw Exception("You don't have any dice left to use")
        if (!currTurn.hasUnusedDiceWithValue(distance)) throw Exception("There's no unused dice with that value")
        //if (distance != board.calculateDistance(from, to)) throw Exception("The distance walked doesn't match the dice distance")

        val piece = player.pieces.find { it.position == from }
        if (piece == null) throw Exception("There's no piece at the position $from")

        val checkPosition = getPieceAt(to)
        checkForPiece(player, checkPosition)

        piece.position = to
        currTurn.actions.add(MoveAction(player, from, to))
    }

    /**
     * Logic for the challenge mechanic of the game. This happens when a player gets a die that has
     * the same number as the [challengeNumber] variable decided at the beginning of the game
     * [player] The name of the player
     * [piecePos] The position of the player's chosen piece
     * [enemyPlayer] The name of the player challenged
     * [enemyPiecePos] The position of the challenged player's chosen piece
     *//*
    fun challenge(player: Player, piecePos: Position, enemyPlayer: Player, enemyPiecePos: Position) {
        if(isOver) throw Exception("The game is over")
        if(player.challenges == 0) throw Exception("You don't have challenges")

        val piece = getPieceAt(piecePos)
        val enemyPiece = getPieceAt(enemyPiecePos)

        if(piece == null || enemyPiece == null) throw Exception("Invalid piece selected")

        val challengeDices = Dice(player, 1, true)
        val challengeDicesEnemy = Dice(enemyPlayer, 1, true)
        challengeDices.roll()
        challengeDicesEnemy.roll()

        val win = challengeDices.values[0] >= challengeDicesEnemy.values[0]

        killPiece(if(win) enemyPiece else piece)

        currTurn.actions.add(ChallengeResultAction(player, win))
    }*/

    private fun applyChallenge(winner: Piece, loser: Piece) {
        TODO("Applies the result of a challenge")
    }

    /**
     * Calls the function to execute according to the item given
     * The player needs to have the item with them
     * [player] The player using the item
     * [item] The item being used
     * [at] The position of the piece the item is being used on
     */
    fun useItem(player: Player, item: Item, at: Position) {
        if(isOver) throw Exception("The game is over")
        if(!currTurn.isPlayersTurn(player)) throw Exception("It's not your turn")
        if(!player.items.contains(item)) throw Exception("You do not possess the item")

        when(item.type) {
            Item.Type.Sword -> useSword(player, at)
            Item.Type.Axe -> TODO()
            Item.Type.Shield -> TODO()
            Item.Type.Bow -> TODO()
            Item.Type.Boots -> TODO()
            Item.Type.Cape -> TODO()
            Item.Type.Dagger -> TODO()
            Item.Type.Robe -> TODO()
            Item.Type.Chestplate -> TODO()
            Item.Type.Rope -> TODO()
            Item.Type.Helm -> TODO()
            Item.Type.Goat -> TODO()
        }
    }

    /**
     * Board and Piece functions
     */

    // Returns a piece given a position, otherwise null
    private fun getPieceAt(position: Position): Piece? {
        return players.flatMap { it.pieces }.find { it.position == position }
    }

    private fun checkNoOverlappingPieces(): Boolean {
        val positions = mutableListOf<Position>()
        players.forEach {
            it.pieces.forEach { piece ->
                if (positions.contains(piece.position))
                    return true
                positions.add(piece.position)
            }
        }
        return false
    }

    private fun checkForPiece(player: Player, piece: Piece?) {
        if(piece != null) {
            if(piece.owner == player.username) throw Exception("Can't move a piece into a position with your own piece")
            players.find { (it.username == piece.owner) }!!.pieces.remove(piece)
        }
    }

    /**
     * Takes hit points away from a piece by a given amount
     * [piece] The piece being damaged
     * [amount] The amount of points being taken
     */
    private fun damagePiece(piece: Piece, amount: Int) {
        piece.hitPoints -= amount
        if(piece.hitPoints <= 0) {
            killPiece(piece)
        }
    }

    /**
     * Takes a piece from play when its hit points reach 0
     * [piece] The piece to be removed
     */
    private fun killPiece(piece: Piece) {
        val player = players.find { (it.username == piece.owner) }
        if(player != null) {
            player.pieces.remove(piece)
            currTurn.actions.add(KillAction(player, piece))
        } else {
            throw Exception("Player not found")
        }
    }

    /**
     * Item Functions
     */

    /**
     * Sword usage, damages pieces that are in the vicinity of a given piece
     * [at] Position to be used in
     */
    private fun useSword(player: Player, at: Position) {
    }
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
