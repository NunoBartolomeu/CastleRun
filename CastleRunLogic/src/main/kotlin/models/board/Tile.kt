package org.example.models.board

import org.example.models.board.position.Position

open class Tile(val position: Position, open val type: Type) {

    enum class Type {
        VOID, WALL, FLOOR, ENTRY, EXIT;

        val value: Int get() = entries.indexOf(this)
    }

    constructor(row: Int, col: Int, type: Type) : this(Position(row, col), type)

}