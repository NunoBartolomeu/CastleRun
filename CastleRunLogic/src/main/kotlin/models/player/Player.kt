package org.example.models.player

import org.example.models.position.Position

data class Player(val username: String, val pieces: List<Piece> = emptyList()) {
    fun movePiece(from: Position, to: Position) {

    }
    fun createPiece(at: Position) {

    }
}