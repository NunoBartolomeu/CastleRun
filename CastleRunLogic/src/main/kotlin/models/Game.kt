package org.example.models

import org.example.logic.random
import org.example.models.board.Board
import org.example.models.board.Tile
import org.example.models.board.position.Position
import org.example.models.player.Piece
import org.example.models.player.Player
import org.example.models.turn.*

data class Game(
    val id: String = "TestGame",
    val board: Board<Tile>,
    val players: List<Player>,
    val rules: GameRules,
    val turns: MutableList<Turn> = mutableListOf(),
    val challengeNumber: Int = random(1, 6)
) {
    val currTurn: Turn get() = turns.last()
    var currPlayer: Player = players[0]

    fun printGame() {
        println("Game: $id")
        println("Players: ${players.joinToString(", ") { it.username }}")
        println()
        board.print(players = players)
    }

    fun printRules() {
        println("Rules: $rules")
    }

    /**
     * The point of decision for the action to be taken
     * [player] The player who is doing a move
     * [position] The list of positions (so to make it easier if there are multiple used)
     * [enemyPlayer] The name of the enemy player in case it applies to the action
     */
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
    }

    fun rollDices(player: Player) {
        if (player != currPlayer) throw Exception("It's not your turn")
        if (currTurn.dices.isNotEmpty()) throw Exception("You already rolled the dices")

        val dice = Dice(player, rules.numDicesToMove, false)
        currTurn.dices.add(dice)
        currTurn.actions.add(RollDiceAction(player.username, dice))
    }

    fun move(player: Player, from: Position, to: Position) {
        checkPlayerAndDice(player)

        val piece = getPieceAt(from) ?: throw Exception("There's no piece in the position $from")
        if (piece.player != player.username) throw Exception("You can't move the enemy's piece")

        val dice = currTurn.dices.last()
        if (dice.used.all { it }) throw Exception("You already used all the dices")

        val distance = from.distanceTo(to)
        if (distance !in dice.values) throw Exception("You can't move the piece $distance tiles")

        val checkPosition = getPieceAt(to)
        checkForPiece(player, checkPosition)

        piece.position = to
        dice.use(distance)
        currTurn.actions.add(MoveAction(player.username, from, to))
    }

    /**
     * Deploys a new piece to the board
     * [player] Player whose piece belongs to
     * [to] Where the piece will be going to
     */
    fun deploy(player: Player, to: Position) {
        checkPlayerAndDice(player)

        val dice = currTurn.dices.last()
        if (dice.used.all { it }) throw Exception("You already used all the dices")

        val checkPosition = getPieceAt(to)
        checkForPiece(player, checkPosition)

        player.pieces.add(Piece(player.username, to))
        currTurn.actions.add(DeployAction(player.username, to))
    }

    /**
     * Logic for the challenge mechanic of the game. This happens when a player gets a die that has
     * the same number as the [challengeNumber] variable decided at the beginning of the game
     * [player] The name of the player
     * [piecePos] The position of the player's chosen piece
     * [enemyPlayer] The name of the player challenged
     * [enemyPiecePos] The position of the challenged player's chosen piece
     */
    fun challenge(player: Player, piecePos: Position, enemyPlayer: Player, enemyPiecePos: Position) {
        checkPlayerAndDice(player)

        val dice = currTurn.dices.last()
        if(dice.values.all { it != challengeNumber }) throw Exception("You didn't get the challenge number")

        val piece = getPieceAt(piecePos)
        val enemyPiece = getPieceAt(enemyPiecePos)

        if(piece == null || enemyPiece == null) throw Exception("Invalid piece selected")

        val challengeDice = Dice(player, 1, true)
        val challengeDiceEnemy = Dice(enemyPlayer, 1, true)
        challengeDice.roll()
        challengeDiceEnemy.roll()

        if(challengeDice.values[0] >= challengeDiceEnemy.values[0]) {
            players.find { (it.username == enemyPiece.player) }!!.pieces.remove(enemyPiece)
        } else {
            players.find { (it.username == piece.player) }!!.pieces.remove(piece)
        }

        currTurn.actions.add(DuelAction(player.username, piece, enemyPiece, 1))
    }

    private fun applyChallenge(winner: Piece, loser: Piece) {
        TODO("Applies the result of a challenge")
    }

    fun useItem() {
        TODO()
    }

    private fun getPieceAt(position: Position): Piece? {
        players.forEach { player ->
            player.pieces.find { it.position == position } ?: return@forEach
        }
        return null
    }

    private fun checkForPiece(player: Player, piece: Piece?) {
        if(piece != null) {
            if(piece.player == player.username) throw Exception("Can't move a piece where your piece lies.")

            players.find { (it.username == piece.player) }!!.pieces.remove(piece)
        }
    }

    private fun checkPlayerAndDice(player: Player) {
        if (player != currPlayer) throw Exception("It's not your turn")
        if (currTurn.dices.isEmpty()) throw Exception("You need to roll the dices first")
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
