package org.example.models.board.position

import kotlin.math.abs

data class Position(val row: Int, val col: Int) {

    operator fun plus(position: Position) = Position(row + position.row, col + position.col)

    operator fun minus(position: Position) =  Position(row - position.row, col - position.col)

    operator fun times(num: Int) = Position(row * num, col * num)

    fun distanceTo(position: Position): Int = abs(row - position.row) + abs(col - position.col)


    fun positionsAtDistance(distance: Int): List<Position> {
        TODO("This function technically works, but it doesn't take walls into account. It's not used in the final implementation.")
        if (distance < 0) 
            throw IllegalArgumentException("Distance must be non-negative")

        if (distance == 0) 
            return listOf(this)

        val positions = mutableListOf<Position>()

        positions.add(Direction.N.asPosition() * distance)
        positions.add(Direction.S.asPosition() * distance)
        positions.add(Direction.E.asPosition() * distance)
        positions.add(Direction.W.asPosition() * distance)

        for (i in 1 until distance) {
            positions.add(this + (Direction.N.asPosition() * i) + (Direction.E.asPosition() * (distance - i)))
            positions.add(this + (Direction.N.asPosition() * i) + (Direction.W.asPosition() * (distance - i)))
            positions.add(this + (Direction.S.asPosition() * i) + (Direction.E.asPosition() * (distance - i)))
            positions.add(this + (Direction.S.asPosition() * i) + (Direction.W.asPosition() * (distance - i)))
        }

        return positions.distinct()
    }
}
