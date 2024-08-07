package org.example.models

import org.example.models.board.Board
import org.example.models.board.Dir
import org.example.models.board.Pos
import org.example.models.board.Position
import org.example.models.board.Tile

class Printer {
    companion object {
        private val tileColors: Map<Tile.Type, String> = mapOf(
            Pair(Tile.Type.VOID, "\u001B[38;2;0;0;0m"),   // Black
            Pair(Tile.Type.ROCK, "\u001B[38;2;160;80;0m"),    // Dark orange
            Pair(Tile.Type.DIRT, "\u001B[38;2;200;200;200m"),   // Light Gray
            Pair(Tile.Type.ENTRY, "\u001B[38;2;0;255;0m"),   // Green
            Pair(Tile.Type.EXIT, "\u001B[38;2;255;50;50m")    // Red
        )

        private val tileSymbols: Map<Tile.Type, String> = mapOf(
            Pair(Tile.Type.VOID, "."),   // Void
            Pair(Tile.Type.ROCK, "X"),    // Wall
            Pair(Tile.Type.DIRT, "_"),   // Floor (changed from 'H' to '.')
            Pair(Tile.Type.ENTRY, "P"),   // Entry
            Pair(Tile.Type.EXIT, "P")    // Exit
        )

        private val pieceColors: Map<Int, String> = mapOf(
            Pair(0, "\u001B[48;2;255;255;0m\u001B[38;2;255;255;255m"), // Red background with white text
            Pair(1, "\u001B[48;2;255;0;255m\u001B[38;2;255;255;255m"), // Green background with white text
            Pair(2, "\u001B[48;2;0;255;255m\u001B[38;2;255;255;255m"), // Blue background with white text
            Pair(3, "\u001B[48;2;255;255;155m\u001B[38;2;0;0;0m")      // Yellow background with black text
        )

        private val pieceSymbols: List<String> = listOf(
            " ", // Piece 1
            " ", // Piece 2
            " ", // Piece 3
            " "  // Piece 4
        )

        fun printGame(game: Game) {
            // Print column labels
            print("\t") // Initial tab for the row labels
            for (col in game.board.tiles[0].indices) {
                print("${col}\t")
            }
            println()

            // Print the board with row labels
            for (rowIndex in game.board.tiles.indices) {
                // Print row label
                print("${rowIndex}\t")

                for (tile in game.board.tiles[rowIndex]) {
                    val piece = game.getPieceAt(tile.position)
                    val (color, symbol) = if (piece == null) {
                        tileColors[tile.type]!! to tileSymbols[tile.type]!!
                    } else {
                        val index = game.players.indexOfFirst { it.username == piece.owner }
                        pieceColors[index]!! to pieceSymbols[index]
                    }
                    print("${color}${symbol}${"\u001B[0m"}\t")
                }
                println()
            }
        }

        fun printPathfinder(
            board: Board,
            start: Position,
            maxDistance: Int,
            directions: List<Dir>,
            colorReduction: Int = 5,
            ignoreWalls: Boolean = false,
            onlyLeafs: Boolean = false,
        ) {
            val gradient = board.pathfinder(start, maxDistance, directions, ignoreWalls, onlyLeafs)

            val colorMap = mutableMapOf<Position, String>()

            // Build color map based on gradient distances
            for (node in gradient) {
                val tile = board[node.position]
                val color = if (tile!!.type == Tile.Type.DIRT) {
                    val r = 255 - (node.distance * colorReduction)
                    "\u001B[38;2;${r};${r};${r}m"
                } else {
                    tileColors[tile.type]!!
                }
                colorMap[node.position] = color
            }

            // Print column labels
            print("\t") // Initial tab for the row labels
            for (col in board.tiles[0].indices) {
                print("${col}\t")
            }
            println()

            // Print the board with row labels
            for (row in board.tiles.indices) {
                // Print row label
                print("${row}\t")

                for (col in board.tiles[row].indices) {
                    val pos = Position(row, col)
                    val tile = board[pos]
                    val symbol = if (gradient.any { it.position == pos }) gradient.find { it.position == pos }?.distance.toString() else "."
                    val color = colorMap[pos] ?: tileColors[tile!!.type]
                    print("${color}${symbol}${"\u001B[0m"}\t")
                }
                println()
            }
        }

        fun printLayout(board: Board) {
            // Print column labels
            print("\t") // Initial tab for the row labels
            for (col in board.tiles[0].indices) {
                print("${col}\t")
            }
            println()

            // Print the board with row labels
            for (row in board.tiles.indices) {
                // Print row label
                print("${row}\t")

                for (tile in board.tiles[row]) {
                    val color = tileColors[tile.type]!!
                    print("${color}${tile.type}\u001B[0m\t")
                }
                println()
            }
        }
    }
}