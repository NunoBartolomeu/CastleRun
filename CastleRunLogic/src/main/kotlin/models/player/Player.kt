package org.example.models.player

data class Player(val username: String) {
    val pieces: MutableList<Piece> = mutableListOf()
    val items: MutableList<Item> = mutableListOf()
}
