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

        fun getNESW(): List<Direction> {
            return listOf(North, East, South, West)
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
}