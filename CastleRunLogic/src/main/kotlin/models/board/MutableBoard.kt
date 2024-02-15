package org.example.models.board

import models.board.Neighbour

open class MutableBoard(
    val numRows: Int,
    val numCols: Int,
    val tiles: Array<Array<Tile>> = voidBoard(numRows, numCols)
) {
    companion object {
        fun voidBoard(numRows: Int, numCols: Int): Array<Array<Tile>> {
            return Array(numRows) { row -> Array(numCols) { col -> Tile(row, col, Tile.Type.VOID) } }
        }
    }

    fun getTile(position: Position) = getTile(position.row, position.col)
    fun getTile(row: Int, col: Int): Tile? {
        return if (row < 0 || row >= numRows || col < 0 || col >= numCols) null else tiles[row][col]
    }

    fun changeTile(position: Position, type: Tile.Type) { tiles[position.row][position.col] = tiles[position.row][position.col].update(type) }
    fun changeTile(row: Int, col: Int, type: Tile.Type) { tiles[row][col] = tiles[row][col].update(type) }

    fun fillOuterWalls() {
        for (i in 0 until numCols) {
            changeTile(0, i, Tile.Type.WALL)
            changeTile(numRows - 1, i, Tile.Type.WALL)
        }
        for (i in 0 until numRows) {
            changeTile(i, 0, Tile.Type.WALL)
            changeTile(i, numCols - 1, Tile.Type.WALL)
        }
    }

    private fun getRandomPosition(): Position {
        val row = (0 until numRows - 1).random()
        val col = (0 until numCols - 1).random()
        return Position(row, col)
    }

    private fun setRandomEntry() {
        changeTile(getRandomPosition(), Tile.Type.ENTRY)
    }
    
    private fun setRandomExit() {
        changeTile(getRandomPosition(), Tile.Type.EXIT)
    }
    
    fun setRandomEntriesAndExits(numEntries: Int, numExits: Int) {
        repeat(numEntries) { setRandomEntry() }
        repeat(numExits) { setRandomExit() }
    }

    fun getEntries() = tiles.flatten().filter { it.type == Tile.Type.ENTRY }
    fun getExits() = tiles.flatten().filter { it.type == Tile.Type.EXIT }

    fun getEntriesAndExits() = getEntries() + getExits()

    fun getNeighbor(tile: Tile, direction: Direction): Neighbour? {
        val n = getTile(tile.position + direction.toPosition()) ?: return null
        return Neighbour(n, direction)
    }

    /**
     * Gets North, South, West and East
     * */
    fun getNeighbors(tile: Tile): List<Neighbour> {
        return listOfNotNull(
            getNeighbor(tile, Direction.North),
            getNeighbor(tile, Direction.East),
            getNeighbor(tile, Direction.South),
            getNeighbor(tile, Direction.West)
        )
    }

    fun getDiagonalNeighbors(tile: Tile): List<Neighbour> {
        return listOfNotNull(
            getNeighbor(tile, Direction.NorthEast),
            getNeighbor(tile, Direction.SouthEast),
            getNeighbor(tile, Direction.SouthWest),
            getNeighbor(tile, Direction.NorthWest)
        )
    }

    fun getAllNeighbors(tile: Tile): List<Neighbour> {
        return getNeighbors(tile) + getDiagonalNeighbors(tile)
    }

    fun toBoard(): Board {
        return Board(numRows, numCols, tiles)
    }

    fun print(showVoid: Boolean = false) {
        for (row in 0 until numRows) {
            for (col in 0 until numCols) {
                val tile = tiles[row][col]
                if (tile.type == Tile.Type.VOID && !showVoid) {
                    print(" ")
                } else {
                    print("${tile.type.value()}")
                }
            }
            println()
        }
    }
}
