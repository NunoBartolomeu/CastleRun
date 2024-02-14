package org.example.models.board

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
    fun getTile(row: Int, col: Int) = tiles[row][col]

    fun updateTile(position: Position, type: Tile.Type) { tiles[position.row][position.col] = tiles[position.row][position.col].update(type) }
    fun updateTile(row: Int, col: Int, type: Tile.Type) { tiles[row][col] = tiles[row][col].update(type) }

    fun fillOuterWalls() {
        for (i in 0 until numCols) {
            updateTile(0, i, Tile.Type.WALL)
            updateTile(numRows - 1, i, Tile.Type.WALL)
        }
        for (i in 0 until numRows) {
            updateTile(i, 0, Tile.Type.WALL)
            updateTile(i, numCols - 1, Tile.Type.WALL)
        }
    }
}
