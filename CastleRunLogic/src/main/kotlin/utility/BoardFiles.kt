package utility

import org.example.models.board.*
import java.io.File

fun boardFromFile(file: File): Board<Tile> {
    if (!file.isFile)
        throw IllegalArgumentException("Path needs to be a file")
    if (file.extension == ".txt")
        throw IllegalArgumentException("File needs to end in \'.txt\'")

    val layout = file.readText()
    return boardFromLayout(layout)
}

fun boardFromLayout(layout: String): Board<Tile> {
    // Remove all characters except digits and newlines
    val cleanedLayout = layout.replace(Regex("[^\\d\n]"), "")

    // Split the cleaned layout by newlines
    val lines = cleanedLayout.split("\n")

    // Ensure each line has the same length and contains only valid digits
    val tiles = Array(lines.size) { row ->
        Array(lines[0].length) { col ->
            val char = lines[row][col]
            if (!char.isDigit()) {
                throw NumberFormatException("Invalid character found: '$char' at position ($row, $col)")
            }
            Tile(row, col, Tile.Type.entries[char.toString().toInt()])
        }
    }
    return Board(tiles)
}

fun boardToFile(board: Board<Tile>, file: File) {
    val json = board.toJson()
    file.writeText(json)
}

fun voidify(inputFile: File, outputFile: File) {
    val board = boardFromFile(inputFile)

    val newTiles = Array(board.numRows) { row ->
        IntArray(board.numCols) { col ->
            val tile = board.tiles[row][col]

            if (tile.type == Tile.Type.WALL) {
                var allVoidOrWall = true

                for (direction in Dir.all) {
                    val newPos = tile.position + direction.asPosition()

                    if (board[newPos] != null) {
                        if (board[newPos]!!.type != Tile.Type.VOID && board[newPos]!!.type != Tile.Type.WALL) {
                            allVoidOrWall = false
                            break
                        }
                    }
                }

                if (allVoidOrWall) {
                    Tile.Type.VOID.value
                } else {
                    tile.type.value
                }
            } else {
                tile.type.value
            }
        }
    }

    val updatedLayout = newTiles.joinToString("\n") { row -> row.joinToString("") { it.toString() } }
    outputFile.writeText(updatedLayout)
}

fun wallify(inputFile: File, outputFile: File) {
    val layout = inputFile.readText()
    val updatedLayout = layout.replace('0', '1')
    outputFile.writeText(updatedLayout)
}

fun main() {
    val inputFile = File("src/main/resources/boards/Board1.txt")
    val outputFile = File("src/main/resources/boards/Board6.txt")

    voidify(inputFile, outputFile)
}
