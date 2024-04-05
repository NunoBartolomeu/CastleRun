package org.example.client

import org.example.models.Game
import org.example.models.board.position.Position
import org.example.models.player.Item
import org.example.models.player.Piece
import org.example.models.player.Player

interface IClient {
    fun move(game: Game, player: Player, piece: Piece, to: Position)

    fun deploy(game: Game, player: Player, at: Position)

    fun useItem(game: Game, player: Player, item: Item, piece: Piece)

    fun endTurn(game: Game, player: Player)
}