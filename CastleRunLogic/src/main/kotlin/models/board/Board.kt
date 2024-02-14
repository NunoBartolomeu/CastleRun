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
            val entries = mutableListOf<Position>()
            for (row in 0 until tiles.size) {
                for (col in 0 until tiles[0].size) {
                    if (tiles[row][col].type == Tile.Type.ENTRY) {
                        entries.add(Position(row, col))
                    }
                }
            }
            return entries
        }

        private fun getExits(tiles: Array<Array<Tile>>): List<Position> {
            val exits = mutableListOf<Position>()
            for (row in 0 until tiles.size) {
                for (col in 0 until tiles[0].size) {
                    if (tiles[row][col].type == Tile.Type.EXIT) {
                        exits.add(Position(row, col))
                    }
                }
            }
            return exits
        }

        private fun getItems(tiles: Array<Array<Tile>>): Map<Position, Item> {
            val items = mutableMapOf<Position, Item>()
            return items
        }
    }

    fun print(showVoid: Boolean = false) {
        for (row in 0 until numRows) {
            for (col in 0 until numCols) {
                val tile = tiles[row][col]
                if (tile.type == Tile.Type.VOID && !showVoid) {
                    print("  ")
                } else {
                    print("${tile.type.value()}")
                }
            }
            println()
        }
    }
}