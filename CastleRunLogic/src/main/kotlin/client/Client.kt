package org.example.client

import org.example.models.Game
import org.example.models.board.position.Position
import org.example.models.player.Item
import org.example.models.player.Piece
import org.example.models.player.Player

class Client: IClient {
    override fun move(game: Game, player: Player, piece: Piece, to: Position) {
        TODO("Not yet implemented")
    }

    override fun deploy(game: Game, player: Player, at: Position) {
        TODO("Not yet implemented")
    }

    override fun useItem(game: Game, player: Player, item: Item, piece: Piece) {
        TODO("Not yet implemented")
    }

    override fun endTurn(game: Game, player: Player) {
        TODO("Not yet implemented")
    }
}