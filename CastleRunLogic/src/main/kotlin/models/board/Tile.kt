package org.example.models.board

open class Tile(val position: Pos, open val type: Type) {
    enum class Type { VOID, WALL, FLOOR, ENTRY, EXIT;
        val value: Int get() = entries.indexOf(this)
    }
    constructor(row: Int, col: Int, type: Type) : this(Pos(row, col), type)
}