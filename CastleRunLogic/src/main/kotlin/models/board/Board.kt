package org.example.models.board

import org.example.models.board.position.Direction
import org.example.models.board.position.Position

open class Board<T: Tile>(val tiles: Array<Array<T>>, val items: Any? = null) {
    val numRows: Int = tiles.size
    val numCols: Int = tiles[0].size
    val entries: List<Position> = tiles.flatten().filter { it.type == Tile.Type.ENTRY }.map { it.position }
    val exits: List<Position> = tiles.flatten().filter { it.type == Tile.Type.EXIT }.map { it.position }
    //val items

    fun inbounds(row: Int, col: Int) = row in 0 until numRows && col in 0 until numCols
    fun inbounds(position: Position) = inbounds(position.row, position.col)

    fun getTile(row: Int, col: Int): Tile? = if (inbounds(row, col)) tiles[row][col] else null
    fun getTile(position: Position) = getTile(position.row, position.col)

    fun getNeighbor(position: Position, direction: Direction): Tile? = getTile(position + direction.asPosition())
    fun getNeighbor(tile: Tile, direction: Direction): Tile? = getNeighbor(tile.position, direction)

    fun getNeighbors(position: Position): List<Pair<Tile, Direction>> {
        return Direction.entries.mapNotNull { direction ->
            return@mapNotNull getNeighbor(position, direction)?.let { neighbor -> neighbor to direction }
        }
    }

    fun getRandomPosition(): Position = Position((0 until numRows).random(), (0 until numCols).random())

    fun getAllTilesAt(tile: Tile, distance: Int): List<Tile> {
        val allPositionsAtDistance = tile.position.positionsAtDistance(distance)
        
        return allPositionsAtDistance.mapNotNull { position ->
            return@mapNotNull getTile(position)
        }
    }

    fun print(showVoid: Boolean = false, showPretty: Boolean = true) {
        for (row in 0 until numRows) {
            for (col in 0 until numCols) {
                val tile = tiles[row][col]
                if (tile.type == Tile.Type.VOID && !showVoid) {
                    print(" ")
                } else {
                    if (showPretty)
                        when(tile.type) {
                            Tile.Type.VOID -> print(" ")
                            Tile.Type.ENTRY -> print("⚐")
                            Tile.Type.EXIT -> print("⚑")
                            Tile.Type.WALL -> print("⍁")
                            Tile.Type.FLOOR -> print(" ")
                        }
                    else
                        print("${tile.type.value}")
                }
            }
            println()
        }
    }
}