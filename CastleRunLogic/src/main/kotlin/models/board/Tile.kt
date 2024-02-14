package org.example.models.board

data class Tile(val position: Position, val type: Type) {
    enum class Type { VOID, WALL, FLOOR, ENTRY, EXIT; }

    constructor(row: Int, col: Int, type: Type) : this(Position(row, col), type)

    fun update(type: Type): Tile {
        return Tile(position, type)
    }
}