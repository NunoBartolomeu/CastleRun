package org.example.logic

import org.example.models.Game
import org.example.models.board.Board
import org.example.models.board.Pos
import org.example.models.board.Position
import org.example.models.board.Tile
import org.example.models.player.Piece
import org.example.models.player.Player
import org.example.models.turn.Turn

/**
 * Contains all the validations necessary to run the game without bugs
 * */
class GameValidator {
    companion object {
        private fun isGameOver(game: Game): Boolean {
            TODO()
        }

        private fun isValidTile(board: Board, pos: Pos, expectedType: Tile.Type?): Boolean {
            val tile = board[pos]?: return false
            return expectedType == null || tile.type == expectedType
        }

        private fun isValidDistance(board: Board, from: Position, to: Position, distance: Int): Boolean {
            val path = board.pathfinder(from, distance, false, true)
            return path.contains(Pair(to, distance))
        }

        private fun isPlayersTurn(turn: Turn, player: Player) = turn.playerUsername == player.username

        fun hasUnusedDice(turn: Turn, value: Int) =
            turn.diceValues.find { it.value == value && !it.used } != null

        private fun hasPieceToDeploy(game: Game, player: Player) =
            game.getActivePieces(player).size < game.gameVariables.maxActivePieces

        private fun hasPieceAt(game: Game, player: Player, position: Position): Boolean {
            val piece = game.getPieceAt(position)?: return false
            return piece.owner == player.username
        }

        fun isValidDamage(game: Game, player: Player, piece: Piece): Boolean {
            if (isGameOver(game))
                throw Exception("The game is over!")

            if (!isValidTile(game.board, piece.position, Tile.Type.FLOOR))
                throw Exception("Invalid tile")

            if (piece != game.getPieceAt(piece.position))
                throw Exception("There's no piece at ${piece.position}")

            if (piece.owner == player.username)
                throw Exception("You can't kill your own pieces")

            return true
        }

        fun isValidKill(game: Game, player: Player, piece: Piece): Boolean {
            if (isGameOver(game))
                throw Exception("The game is over!")

            if (!isValidTile(game.board, piece.position, Tile.Type.FLOOR))
                throw Exception("Invalid tile")

            if (piece != game.getPieceAt(piece.position))
                throw Exception("There's no piece at ${piece.position}")

            if (piece.owner == player.username)
                throw Exception("You can't kill your own pieces")

            if (piece.hitPoints > 0)
                throw Exception("Piece still has ${piece.hitPoints} health")

            return true
        }

        fun isValidDeploy(game: Game, player: Player, entry: Position, to: Position, distance: Int): Boolean {
            if (isGameOver(game))
                throw Exception("The game is over!")

            if (!isValidTile(game.board, entry, Tile.Type.ENTRY))
                throw Exception("Invalid entry")

            if (!isValidTile(game.board, to, Tile.Type.FLOOR))
                throw Exception("Can't deploy a piece on a non-floor tile")

            val currTurn = game.getCurrTurn()
            if (!isPlayersTurn(currTurn, player))
                throw Exception("It's not ${player.username} turn!")

            if (hasUnusedDice(currTurn, distance))
                throw Exception("There's no unused dice with that value")

            if (hasPieceToDeploy(game, player))
                throw Exception("You don't have any pieces left to deploy")

            if (isValidDistance(game.board, entry, to, distance))
                throw Exception("The distance walked doesn't match the real distance")

            return true
        }

        fun isValidMove(game: Game, player: Player, from: Position, to: Position, distance: Int): Boolean {
            if (isGameOver(game))
                throw Exception("The game is over!")

            if (!isValidTile(game.board, from, Tile.Type.FLOOR))
                throw Exception("Invalid tile $from")

            if (!isValidTile(game.board, to, Tile.Type.FLOOR))
                if (!isValidTile(game.board, to, Tile.Type.EXIT))
                    throw Exception("Can't move a piece to tile $to")

            val currTurn = game.getCurrTurn()
            if (!isPlayersTurn(currTurn, player))
                throw Exception("It's not ${player.username} turn!")

            if (hasUnusedDice(currTurn, distance))
                throw Exception("There's no unused dice with that value")

            if (hasPieceAt(game, player, from))
                throw Exception("You don't have a piece at $from")

            if (isValidDistance(game.board, from, to, distance))
                throw Exception("The distance between $from and $to is not $distance")

            return true
        }

        fun isValidEndTurn(game: Game, player: Player): Boolean {
            if (isGameOver(game))
                throw Exception("The game is over!")

            val currTurn = game.getCurrTurn()
            if (!isPlayersTurn(currTurn, player))
                throw Exception("It's not ${player.username} turn!")

            return true
        }
    }
}