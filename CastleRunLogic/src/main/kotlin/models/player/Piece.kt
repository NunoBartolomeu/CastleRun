package org.example.models.player

import org.example.models.board.position.Position

data class Piece(val owner: String, var position: Position, val effects: String? = null, var hitPoints: Int = 1)