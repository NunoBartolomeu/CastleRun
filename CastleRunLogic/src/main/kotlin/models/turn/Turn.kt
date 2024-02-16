package org.example.models.turn

import org.example.models.items.Item
import org.example.models.player.Piece
import org.example.models.position.Position

class Turn(val number: Int) {
    val history: MutableList<Event> = mutableListOf()

    data class Move(val piece: Piece, val from: Position, val to: Position)
    data class Deploy(val piece: Piece, val at: Position)
    data class Duel(val allyPiece: Piece, val enemyPiece: Piece, val number: Int)
    data class UseItem(val item: Item, val piece: Piece)

}