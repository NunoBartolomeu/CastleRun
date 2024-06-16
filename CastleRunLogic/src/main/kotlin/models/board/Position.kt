package org.example.models.board

typealias Pos = Position

data class Position(val row: Int, val col: Int) {
    operator fun plus(position: Position) = Position(row + position.row, col + position.col)
    operator fun minus(position: Position) = Position(row - position.row, col - position.col)
    operator fun times(num: Int) = Position(row * num, col * num)
}