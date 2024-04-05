package org.example.models

import org.example.logic.random
import org.example.models.board.Board
import org.example.models.board.Tile
import org.example.models.board.position.Position
import org.example.models.player.Piece
import org.example.models.player.Player
import org.example.models.turn.*

data class Game(
    val id: String,
    val board: Board<Tile>,
    val players: List<Player>,
    val rules: GameRules,
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
        val dices = List(rules.numDicesToMove) { Dice(it, random(rules.minDiceValue, rules.maxDiceValue), false) }
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
        if (board.getTile(to)!!.type != Tile.Type.FLOOR) throw Exception("Can't deploy a piece on a non-floor tile")
        if (distance != board.calculateDistance(entry, to)) throw Exception("The distance walked doesn't match the real distance")

        val checkPosition = getPieceAt(to)
        checkForPiece(player, checkPosition)

        player.pieces.add(Piece(player.username, to))
        currTurn.dices.find { !it.wasUsed && it.value == distance }!!.wasUsed = true

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
        if (currTurn.isPlayersTurn(player)) throw Exception("It's not your turn")
        if (currTurn.hasUnusedDice()) throw Exception("You don't have any dice left to use")
        if (currTurn.hasUnusedDiceWithValue(distance)) throw Exception("There's no unused dice with that value")
        if (distance != board.calculateDistance(from, to)) throw Exception("The distance walked doesn't match the dice distance")

        val piece = player.pieces.find { it.position == from }
        if (piece == null) throw Exception("There's no piece at the position $from")

        val checkPosition = getPieceAt(to)
        checkForPiece(player, checkPosition)

        piece.position = to
        currTurn.actions.add(MoveAction(player, from, to))
    }

    /**
     * The point of decision for the action to be taken
     * [player] The player who is doing a move
     * [position] The list of positions (so to make it easier if there are multiple used)
     * [enemyPlayer] The name of the enemy player in case it applies to the action
     *//*
    fun play(player: Player, action: String, position: List<Position>, enemyPlayer: Player) {
        when(action) {
            "rollDice" -> {
                if(position.isNotEmpty()) throw Exception("Rolling the dice does not need positions")
                rollDices(player)
            }
            "move" -> {
                if(position.size != 2) throw Exception("Wrong number of positions for action move")
                move(player, position[0], position[1])
            }
            "deploy" -> {
                if(position.size != 1) throw Exception("Wrong number of positions for action deploy")
                deploy(player, position[0])
            }
            "challenge" -> {
                if(position.size != 2) throw Exception("Wrong number of positions for action challenge")
                challenge(player, position[0], enemyPlayer, position[1])
            }
        }
    }*/
  

    /**
     * Logic for the challenge mechanic of the game. This happens when a player gets a die that has
     * the same number as the [challengeNumber] variable decided at the beginning of the game
     * [player] The name of the player
     * [piecePos] The position of the player's chosen piece
     * [enemyPlayer] The name of the player challenged
     * [enemyPiecePos] The position of the challenged player's chosen piece
     */
    /*
    fun challenge(dices: Dices, player: Player, piecePos: Position, enemyPlayer: Player, enemyPiecePos: Position) {
        checkPlayerAndDice(player)

        if(!currTurn.dices.contains(dices)) throw Exception("Invalid dice")
        if(dices.values.all { it != challengeNumber }) throw Exception("You didn't get the challenge number")

        val piece = getPieceAt(piecePos)
        val enemyPiece = getPieceAt(enemyPiecePos)

        if(piece == null || enemyPiece == null) throw Exception("Invalid piece selected")

        val challengeDices = Dices(player, 1, true)
        val challengeDicesEnemy = Dices(enemyPlayer, 1, true)
        challengeDices.roll()
        challengeDicesEnemy.roll()

        if(challengeDices.values[0] >= challengeDicesEnemy.values[0]) {
            players.find { (it.username == enemyPiece.owner) }!!.pieces.remove(enemyPiece)
        } else {
            players.find { (it.username == piece.owner) }!!.pieces.remove(piece)
        }

        currTurn.actions.add(DuelAction(player.username, piece, enemyPiece, 1))
    }
    */
    private fun applyChallenge(winner: Piece, loser: Piece) {
        TODO("Applies the result of a challenge")
    }

    fun useItem() {
        TODO()
    }

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
