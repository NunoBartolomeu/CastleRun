package org.example.logic

import org.example.models.Game
import org.example.models.board.Board
import org.example.models.board.Dir
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
        private fun isGameOver(game: Game) {
            TODO()
            throw Exception("Game has ended")
        }

        private fun isValidTile(board: Board, pos: Pos, expectedType: Tile.Type?) {
            val tile = board[pos]?: throw Exception("Tile is not in board")

            if(expectedType != null && tile.type != expectedType)
                throw Exception("Tile type doesn't match, expected: $expectedType, got: ${tile.type}")
        }

        private fun isValidDistance(board: Board, from: Position, to: Position, distance: Int) {
            // TODO: Need to fix how pathfinder is used
            val path = board.pathfinder(from, distance, Dir.cardinal, true)
            val node = path.find { it.position == to } ?: throw Exception("PathFinder did not get: $to")

            if(node.distance != distance) throw Exception("Distance $distance does not match ${node.distance}")
        }

        private fun isPlayersTurn(turn: Turn, player: Player) {
            if(turn.playerUsername != player.username)
                throw Exception("Turn belongs to ${turn.playerUsername} not ${player.username}")
        }

        fun hasUnusedDice(turn: Turn, value: Int) {
            turn.diceValues.find { it.value == value && !it.used }?:
            throw Exception("Has no unused dice with value $value")
        }

        private fun hasPieceToDeploy(game: Game, player: Player) {
            if(game.getActivePieces(player).size >= game.gameVariables.maxActivePieces)
                throw Exception("Player ${player.username} already deployed all pieces")
        }

        private fun doesPieceExistAt(game: Game, position: Position) {
            game.getPieceAt(position)?: throw Exception("There is no piece at $position")
        }

        private fun doesPieceBelongTo(player: Player, piece: Piece) {
            if(piece.owner != player.username) throw Exception("Piece does not belong to player $player")
        }

        /* **************************************************************************************************** */

        fun isValidDamage(game: Game, player: Player, piece: Piece) {
            isGameOver(game)
            isValidTile(game.board, piece.position, Tile.Type.FLOOR)
            doesPieceExistAt(game, piece.position)
            doesPieceBelongTo(game.getPlayer(piece.owner), piece)

            if (piece.owner == player.username)
                throw Exception("You can't kill your own pieces")
        }

        fun isValidKill(game: Game, player: Player, piece: Piece) {
            isGameOver(game)
            isValidTile(game.board, piece.position, Tile.Type.FLOOR)
            doesPieceExistAt(game, piece.position)

            if (piece.owner == player.username)
                throw Exception("You can't kill your own pieces")

            if (piece.hitPoints > 0)
                throw Exception("Piece still has ${piece.hitPoints} health")
        }

        fun isValidDeploy(game: Game, player: Player, entry: Position, to: Position, distance: Int) {
            isGameOver(game)
            isValidTile(game.board, entry, Tile.Type.ENTRY)
            isValidTile(game.board, to, Tile.Type.FLOOR)

            val currTurn = game.getCurrTurn()

            isPlayersTurn(currTurn, player)
            hasUnusedDice(currTurn, distance)
            hasPieceToDeploy(game, player)
            isValidDistance(game.board, entry, to, distance)
        }

        fun isValidMove(game: Game, player: Player, from: Position, to: Position, distance: Int) {
            isGameOver(game)
            isValidTile(game.board, from, Tile.Type.FLOOR)

            // TODO: Fix the floor and exit logic
           /*if (!isValidTile(game.board, to, Tile.Type.FLOOR))
                if (!isValidTile(game.board, to, Tile.Type.EXIT))*/

            val currTurn = game.getCurrTurn()

            isPlayersTurn(currTurn, player)
            hasUnusedDice(currTurn, distance)
            doesPieceExistAt(game, from)

            val piece = game.getPieceAt(from)

            doesPieceBelongTo(player, piece!!)
            isValidDistance(game.board, from, to, distance)
        }

        fun isValidEndTurn(game: Game, player: Player) {
            isGameOver(game)

            val currTurn = game.getCurrTurn()
            isPlayersTurn(currTurn, player)
        }
    }
}