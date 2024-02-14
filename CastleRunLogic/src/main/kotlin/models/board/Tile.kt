package org.example.models.board

data class Tile(val position: Position, val type: Type) {
    enum class Type { VOID, WALL, FLOOR, ENTRY, EXIT;
        fun value() = value(this)
    }

    constructor(row: Int, col: Int, type: Type) : this(Position(row, col), type)

    fun update(type: Type): Tile {
        return Tile(position, type)
    }

    companion object {
        fun value(type: Type) = when (type) {
            Type.VOID -> 0
            Type.WALL -> 1
            Type.FLOOR -> 2
            Type.ENTRY -> 3
            Type.EXIT -> 4
        }
    }

}