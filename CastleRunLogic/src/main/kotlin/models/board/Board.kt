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

    data class Node(val position: Pos, val previous: Node?, val distance: Int)

    fun pathfinder (
        start: Position,
        distance: Int,
        directions: List<Dir>,
        ignoreWalls: Boolean = false,
        onlyLeafs: Boolean = false,
    ): List<Node> {
        val queue: LinkedList<Node> = LinkedList()
        val visited: MutableSet<Position> = mutableSetOf()
        val result: MutableList<Node> = mutableListOf()

        val startNode = Node(start, null, 0)
        queue.add(startNode)
        visited.add(start)
        result.add(startNode)

        while (queue.isNotEmpty()) {
            val currNode = queue.removeFirst()
            val currPos = currNode.position
            val currDistance = currNode.distance

            if (currDistance >= distance)
                continue

            for (direction in directions) {
                val newPos = currPos + direction.asPosition()
                if (visited.contains(newPos) || !isValidTile(newPos, ignoreWalls)) continue

                val newNode = Node(newPos, currNode, currDistance + 1)
                queue.add(newNode)
                visited.add(newPos)
                result.add(newNode)
            }
        }

        if (onlyLeafs)
            return result.filter { this[it.position]!!.type == Tile.Type.EXIT || it.distance == distance }
        return result
    }
}
