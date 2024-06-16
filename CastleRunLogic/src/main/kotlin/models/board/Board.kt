package org.example.models.board

import utility.boardFromFile
import java.io.File
import java.util.*

open class Board<T: Tile>(val tiles: Array<Array<T>>, val items: Any? = null) {
    val numRows: Int = tiles.size
    val numCols: Int = tiles[0].size
    val entries: List<Pos> = tiles.flatten().filter { it.type == Tile.Type.ENTRY }.map { it.position }
    val exits: List<Pos> = tiles.flatten().filter { it.type == Tile.Type.EXIT }.map { it.position }

    ////////////////////////////// Tile Logic //////////////////////

    //Makes it possible to call board[0][0] or board[0, 0]
    //operator fun get(row: Int): Array<T>? = if (inbounds(null, row, 0)) tiles[row] else null
    //operator fun get(row: Int, col: Int): Tile? = if (inbounds(null, row, col)) tiles[row][col] else null

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

    fun printGradientMap(
        start: Position,
        maxDistance: Int,
        colorReduction: Int = 5,
        ignoreWalls: Boolean = false,
        onlyCardinal: Boolean = false,
        onlyDiagonals: Boolean = false
    ) {
        val gradient = this.pathfinder(start, maxDistance, ignoreWalls, onlyCardinal, onlyDiagonals)
            ?: throw IllegalArgumentException("No path found within max distance.")

        val colorMap = mutableMapOf<Position, String>()

        // Build color map based on gradient distances
        for ((pos, dist) in gradient) {
            val tile = this[pos]
            val color = when (tile!!.type) {
                Tile.Type.VOID -> "\u001B[38;5;0m"    // Black
                Tile.Type.WALL -> "\u001B[38;5;88m"    // Dark Red
                Tile.Type.FLOOR -> {
                    val r = 255 - (dist * colorReduction)
                    "\u001B[38;2;${r};${r};${r}m" // Adjust green color dynamically
                }
                Tile.Type.ENTRY -> "\u001B[38;5;39m"   // Blue
                Tile.Type.EXIT -> "\u001B[38;5;35m"    // Green
            }
            colorMap[pos] = color
        }

        // Print the board with distances or dots based on colorMap
        for (row in tiles.indices) {
            for (col in tiles[row].indices) {
                val pos = Position(row, col)
                val tile = this[pos]
                val symbol = if (gradient.any { it.first == pos }) gradient.find { it.first == pos }?.second.toString() else "."
                val color = colorMap[pos] ?: when (tile?.type) {
                    Tile.Type.VOID -> "\u001B[38;5;0m"    // Black
                    Tile.Type.WALL -> "\u001B[38;5;88m"    // Dark Red
                    Tile.Type.FLOOR -> "\u001B[38;5;255m"   // White
                    Tile.Type.ENTRY -> "\u001B[38;5;39m"   // Blue
                    Tile.Type.EXIT -> "\u001B[38;5;35m"    // Green
                    else -> "\u001B[38;5;0m" // Default to black for unknown types
                }
                print("${color}${symbol}${"\u001B[0m"}\t")
            }
            println()
        }
    }

    fun printBoardValues() {
        for (row in tiles) {
            for (tile in row) {
                val color = when (tile.type) {
                    Tile.Type.VOID -> "\u001B[38;5;0m"    // Black
                    Tile.Type.WALL -> "\u001B[38;5;88m"    // Dark Red
                    Tile.Type.FLOOR -> "\u001B[38;5;255m"   // White
                    Tile.Type.ENTRY -> "\u001B[38;5;39m"   // Blue
                    Tile.Type.EXIT -> "\u001B[38;5;35m"    // Green
                }
                print("${color}${tile.type.value}\u001B[0m\t")
            }
            println()
        }
    }
}