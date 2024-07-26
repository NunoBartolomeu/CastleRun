package org.example.models.player

import org.example.logger.RGB

data class Player(val username: String, val color: RGB) {
    val pieces: MutableList<Piece> = mutableListOf()
    val items: MutableList<Item> = mutableListOf()
}
