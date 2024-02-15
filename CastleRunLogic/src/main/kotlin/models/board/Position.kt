package org.example.models.board

data class Position(val row: Int, val col: Int) {
    operator fun plus(position: Position): Position {
        return Position(row + position.row, col + position.col)
    }
}
