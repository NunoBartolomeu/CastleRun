package org.example.models

import java.io.File

data class Board(
    val tiles: List<Tile>,
    val entries: List<Coords> = getEntries(tiles),
    val exits : List<Coords> = getExits(tiles),
    val equipment: Map<Coords, Equipment> = mapOf()
) {
    fun visualization() {
        for (tile in tiles)
            tile.type.printTile()
    }

    companion object {
        fun fromFile(path: File): Board {
            TODO("Implement David")
        }

        fun toFile(path: File): Board {
            TODO("Implement David")
        }

        private fun getEntries(tiles: List<Tile>): List<Coords> {
            val entries = mutableListOf<Coords>()
            for (tile in tiles)
                if (tile.type == Tile.Type.Entry)
                    entries.add(tile.coords)
            return entries
        }
        private fun getExits(tiles: List<Tile>): List<Coords> {
            val exits = mutableListOf<Coords>()
            for (tile in tiles)
                if (tile.type == Tile.Type.Entry)
                    exits.add(tile.coords)
            return exits
        }

        fun getTiles(numRows: Int, numCols: Int, walls: List<Coords>, entries: List<Coords>, exits: List<Coords>): List<Tile> {
            val tiles = mutableListOf<Tile>()
            for (row in 0 until numRows) {
                for (col in 0 until numCols) {
                    val coords = Coords(row, col)
                    val type = when {
                        walls.contains(coords) -> Tile.Type.Wall
                        entries.contains(coords) -> Tile.Type.Entry
                        exits.contains(coords) -> Tile.Type.Exit
                        else -> Tile.Type.Floor
                    }
                    tiles.add(Tile(coords, type))
                }
            }
            return tiles
        }

        fun randomEquipment(tiles: List<Tile>, percentageOfEquipment: Int): Map<Coords, Equipment> {
            TODO("Implement David")
        }
    }
}

