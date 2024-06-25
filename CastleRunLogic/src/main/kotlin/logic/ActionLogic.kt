package org.example.logic

import org.example.models.Game
import org.example.models.board.Position
import org.example.models.player.Piece
import org.example.models.player.Player

class ActionLogic {
    companion object {
        fun deploy(game: Game, player: Player, entry: Position, to: Position, distance: Int) {
            if (GameValidator.isValidDeploy(game, player, entry, to, distance)) {
                //If there's an enemy at the new position, kill it
                val piece = game.getPieceAt(to)
                if (piece != null)
                    GameLogic.damagePiece(game, player, piece, game.gameVariables.piecesHp)
                //TODO validate if stomping is always a kill or not
                //Add the piece to the Player
                player.pieces.add(Piece(player.username, to, game.gameVariables.piecesHp))
                //Use the dice
                GameLogic.useDice(game, distance)
            }
            else
                throw Error("Deploy is invalid!")
        }

        fun move(game: Game, player: Player, from: Position, to: Position, distance: Int) {
            if (GameValidator.isValidMove(game, player, from, to, distance)) {
                //If there's an enemy at the new position, kill it
                val pieceTo = game.getPieceAt(to)
                if (pieceTo != null)
                    GameLogic.damagePiece(game, player, pieceTo, game.gameVariables.piecesHp)
                //TODO validate if stomping is always a kill or not

                //Move the piece for the Player
                val pieceFrom = game.getPieceAt(from)!!
                pieceFrom.position = to

                //Use the dice
                GameLogic.useDice(game, distance)

                //TODO add an item if the tile has items
            }
            else
                throw Error("Move is invalid!")
        }
    }
}