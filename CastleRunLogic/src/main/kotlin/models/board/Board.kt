package org.example.models.board

import Node
import org.example.models.board.position.Direction
import org.example.models.board.position.Position
import org.example.models.player.Player

open class Board<T: Tile>(val tiles: Array<Array<T>>, val items: Any? = null) {
    val numRows: Int = tiles.size
    val numCols: Int = tiles[0].size
    val entries: List<Position> = tiles.flatten().filter { it.type == Tile.Type.ENTRY }.map { it.position }
    val exits: List<Position> = tiles.flatten().filter { it.type == Tile.Type.EXIT }.map { it.position }
    //val items

    fun inbounds(position: Position) = position.row in 0 until numRows && position.col in 0 until numCols

    fun getTile(position: Position): Tile? = if (inbounds(position)) tiles[position.row][position.col] else null

    fun getNeighbor(position: Position, direction: Direction): Tile? = getTile(position + direction.asPosition())

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

    fun getTree(start: Position, distance: Int): Node? {
        return getNode(start, null, distance)
    }

    private fun getNode(position: Position, previous: Position?, distance: Int): Node? {
        if (inbounds(position) || distance < 0 || tiles[position.row][position.col].type == Tile.Type.ENTRY)
            return null

        if (distance == 0 || tiles[position.row][position.col].type == Tile.Type.EXIT)
            return Node(position, previous, null, distance)

        val next = mutableListOf<Node>()
        val neighbors = getNeighbors(position)

        for (n in neighbors) {
            val node = getNode(n.first.position, position, distance - 1)
            if (node != null) next.add(node)
        }

        return Node(position, previous, next, distance)
    }

    fun print(showPretty: Boolean = true, players: List<Player> = emptyList()) {
        //Print column numbers
        print("\uD83D\uDD38")
        for (col in 0 until numCols) {
            print("${col%10}\uFE0F⃣")
        }
        println()

        for (row in 0 until numRows) {
            //Print row numbers
            print("${row%10}\uFE0F⃣")

            for (col in 0 until numCols) {
                val tile = tiles[row][col]
                if (showPretty)
                    if (players.isNotEmpty() && players.any { it.pieces.any { it.position == tile.position } }) {
                        printPiece(players.indexOfFirst { it.pieces.any { it.position == tile.position } })
                    }
                    else printTile(tile)
                else
                    print("${tile.type.value}")
            }
            println()
        }
    }

    private fun printTile(tile: Tile) {
        when(tile.type) {
            Tile.Type.ENTRY -> print("\uD83C\uDD70\uFE0F")
            Tile.Type.EXIT -> print("\uD83C\uDD71\uFE0F")
            Tile.Type.WALL -> print("⬛")
            Tile.Type.FLOOR -> print("\u2B1C")
            Tile.Type.VOID -> print("\uD83D\uDD73\uFE0F")
        }
    }

    private fun printPiece(playerIdx: Int) {
        when(playerIdx) {
            0 -> print("\uD83D\uDFE1")
            1 -> print("\uD83D\uDFE2")
            2 -> print("\uD83D\uDD35")
            3 -> print("\uD83D\uDFE0")
            else -> error("More than 4 players are not supported")
        }
    }
}