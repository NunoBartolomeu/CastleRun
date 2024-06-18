package org.example.models.board

import java.util.*

data class Board(val tiles: Array<Array<Tile>>, val items: Any? = null) {
    val numRows: Int = tiles.size
    val numCols: Int = tiles[0].size

    fun getEntries(): List<Pos> = tiles.flatten().filter { it.type == Tile.Type.ENTRY }.map { it.position }
    fun getExits(): List<Pos> = tiles.flatten().filter { it.type == Tile.Type.EXIT }.map { it.position }

    ////////////////////////////// Tile Logic //////////////////////

    //Makes it possible to call board[pos]
    operator fun get(pos: Pos): Tile? = if (inbounds(pos)) tiles[pos.row][pos.col] else null

    //Can be called using an object position or writing (row, col)
    private fun inbounds(pos: Pos? = null, row: Int = -1, col: Int = -1): Boolean {
        return (pos?.row ?: row) in 0 until numRows && (pos?.col ?: col) in 0 until numCols
    }

    fun getRandomPosition(): Pos = Pos((0 until numRows).random(), (0 until numCols).random())

    private fun isValidTile(pos: Position, ignoreWalls: Boolean = false): Boolean {
        val tile = this[pos]
        if (tile == null || tile.type == Tile.Type.VOID) return false
        if (tile.type == Tile.Type.WALL && !ignoreWalls) return false
        if (tile.type == Tile.Type.WALL && isWallNextToVoid(pos)) return false
        return true
    }

    private fun isWallNextToVoid(pos: Position): Boolean {
        val directions = Direction.entries.map { it.asPosition() }
        val neighbors = directions + listOf(Position(-1, 0), Position(1, 0), Position(0, -1), Position(0, 1))

        for (neighbor in neighbors) {
            val newPos = pos + neighbor
            if (!inbounds(newPos)) {
                // If newPos is out of bounds, consider it as next to void
                return true
            }
            if (this[newPos]?.type == Tile.Type.VOID) {
                return true
            }
        }
        return false
    }

    //////////////////////////// Pathfinding Logic ////////////////////////////

    fun pathfinder (
        start: Position,
        distance: Int,
        ignoreWalls: Boolean = false,
        onlyCardinal: Boolean = false,
        onlyDiagonals: Boolean = false
    ): List<Pair<Position, Int>> {
        val directions =
            if (onlyCardinal) Dir.cardinal
            else if (onlyDiagonals) Dir.diagonal
            else Dir.all

        val queue: LinkedList<Pair<Position, Int>> = LinkedList()
        val visited: MutableSet<Position> = mutableSetOf()
        val result: MutableList<Pair<Position, Int>> = mutableListOf()

        queue.add(start to 0)
        visited.add(start)
        result.add(start to 0)

        while (queue.isNotEmpty()) {
            val (currentPos, currentDistance) = queue.removeFirst()

            if (currentDistance >= distance) continue

            for (direction in directions) {
                val newPos = currentPos + direction.asPosition()
                if (visited.contains(newPos) || !isValidTile(newPos, ignoreWalls)) continue

                queue.add(newPos to currentDistance + 1)
                visited.add(newPos)
                result.add(newPos to currentDistance + 1)
            }
        }
        return result
    }
}