package org.example.models.player

data class Player(val username: String) {
    val pieces: MutableList<Piece> = mutableListOf()
    private val items: MutableList<Item> = mutableListOf()
    val piecesAtExit: Int get() = pieces.size
}