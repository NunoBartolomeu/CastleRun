package org.example.logic

import org.example.models.Game
import org.example.models.board.Position
import org.example.models.player.Piece
import org.example.models.player.Player
import org.example.models.turn.Turn

/**
 * Contains a way to move and deploy pieces
 * Contains turn management functions
 */
class GameLogic {
    companion object {
        fun damagePiece(game: Game, player: Player, piece: Piece, damage: Int) {
            GameValidator.isValidDamage(game, player, piece)

            //Damage the piece
            piece.hitPoints -= damage

            //If the health is 0 or less kill the piece
            if (piece.hitPoints <= 0)
                killPiece(game, player, piece)
        }

        private fun killPiece(game: Game, player: Player, piece: Piece) {
            GameValidator.isValidKill(game, player, piece)

            //Remove the piece
            game.getPlayer(piece.owner).pieces.remove(piece)
        }

        fun useDice(game: Game, value: Int) {
            GameValidator.hasUnusedDice(game.getCurrTurn(), value)
            game.getCurrTurn().diceValues.find { it.value == value && !it.used }!!.used = true
        }

        fun move(game: Game, player: Player, from: Position, to: Position, distance: Int) {
            GameValidator.isValidMove(game, player, from, to, distance)
            //If there's an enemy at the new position, kill it
            val pieceTo = game.getPieceAt(to)
            if (pieceTo != null)
                damagePiece(game, player, pieceTo, game.gameVariables.piecesHp)
            //TODO validate if stomping is always a kill or not

            //Move the piece for the Player
            val pieceFrom = game.getPieceAt(from)!!
            pieceFrom.position = to

            //Use the dice
            useDice(game, distance)

            //TODO add an item if the tile has items
        }

        private fun getNextPlayer(game: Game): Player {
            if (game.turns.isEmpty())
                return game.players.first()
            val currTurn = game.getCurrTurn()
            val index = game.players.indexOfFirst { it.username == currTurn.playerUsername }
            return if (index == game.players.size - 1) game.players[0] else game.players[index + 1]
        }

        fun startTurn(game: Game) {
            val number = if (game.turns.isEmpty()) 0 else game.getCurrTurn().number + 1
            val dices = List(game.dices.size) { game.dices[it].roll() }
            val turn = Turn(number, getNextPlayer(game).username, dices)
            game.turns.add(turn)
        }

        fun endTurn(game: Game, player: Player) {
            GameValidator.isValidEndTurn(game, player)
        }

        fun endGame(game: Game) {

        }
    }
}
