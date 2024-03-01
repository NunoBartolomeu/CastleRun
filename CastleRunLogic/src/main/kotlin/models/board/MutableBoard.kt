package org.example.models.board

import org.example.models.board.position.Position

class MutableBoard(numRows: Int, numCols: Int): Board<MutableTile>(voidBoard(numRows, numCols)) {
    companion object {
        fun voidBoard(numRows: Int, numCols: Int) = Array(numRows) { row -> Array(numCols) { col -> MutableTile(row, col, Tile.Type.VOID) } }
    }

    fun setTile(row: Int, col: Int, type: Tile.Type) {
        tiles[row][col] = MutableTile(row, col, type)
    }
    fun setTile(position: Position, type: Tile.Type) = setTile(position.row, position.col, type)
    fun setTile(tile: Tile, type: Tile.Type) = setTile(tile.position, type)

    fun fillOuterWalls() {
        for (i in 0 until numCols) {
            setTile(0, i, Tile.Type.WALL)
            setTile(numRows - 1, i, Tile.Type.WALL)
        }
        for (i in 0 until numRows) {
            setTile(i, 0, Tile.Type.WALL)
            setTile(i, numCols - 1, Tile.Type.WALL)
        }
    }

    private fun setRandom(type: Tile.Type) = setTile(getRandomPosition(), type)
    
    fun setRandomEntriesAndExits(numEntries: Int, numExits: Int) {
        repeat(numEntries) { setRandom(Tile.Type.ENTRY) }
        repeat(numExits) { setRandom(Tile.Type.EXIT) }
    }

    fun toBoard(): Board<Tile> {
        val newTiles: Array<Array<Tile>> = Array(numRows) { row -> Array(numCols) { col -> tiles[row][col] } }
        return Board(newTiles)
    }
}
