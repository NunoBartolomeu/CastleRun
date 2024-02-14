package org.example.models.board

import org.example.models.items.Item

data class Board(
    val numRows: Int,
    val numCols: Int,
    val tiles: Array<Array<Tile>>,
    val entries: List<Position> = getEntries(tiles),
    val exits: List<Position> = getExits(tiles),
    val items: Map<Position, Item> = getItems(tiles)
) {
    companion object {
        private fun getEntries(tiles: Array<Array<Tile>>): List<Position> {
            TODO("David Implementa")
        }

        private fun getExits(tiles: Array<Array<Tile>>): List<Position> {
            TODO("David Implementa")
        }

        private fun getItems(tiles: Array<Array<Tile>>): Map<Position, Item> {
            TODO("David Implementa")
        }
    }
}