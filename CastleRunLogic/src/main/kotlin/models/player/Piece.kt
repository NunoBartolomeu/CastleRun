package org.example.models.player

import org.example.models.items.Item
import org.example.models.position.Position

data class Piece(var position: Position, var item: Item? = null) {
    fun giveItem(item: Item) {
        this.item = item
    }
}