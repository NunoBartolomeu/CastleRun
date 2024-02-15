package org.example.models.board

enum class Direction {
    North,
    East,
    South,
    West,
    NorthEast,
    NorthWest,
    SouthEast,
    SouthWest;

    companion object {
        fun fromString(value: String): Direction = when (value) {
            "N" -> North
            "E" -> East
            "S" -> South
            "W" -> West
            "NE" -> NorthEast
            "NW" -> NorthWest
            "SE" -> SouthEast
            "SW" -> SouthWest
            else -> throw IllegalArgumentException("Invalid direction: $value")
        }
    }

    override fun toString(): String {
        return when (this) {
            North -> "N"
            East -> "E"
            South -> "S"
            West -> "W"
            NorthEast -> "NE"
            NorthWest -> "NW"
            SouthEast -> "SE"
            SouthWest -> "SW"
        }
    }

    fun toPosition(): Position {
        return when (this) {
            North -> Position(-1, 0)
            East -> Position(0, 1)
            South -> Position(1, 0)
            West -> Position(0, -1)
            NorthEast -> Position(-1, 1)
            NorthWest -> Position(-1, -1)
            SouthEast -> Position(1, 1)
            SouthWest -> Position(1, -1)
        }
    }
}