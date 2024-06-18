package org.example.models.board

data class Tile(val position: Pos, val type: Type) {
    enum class Type { VOID, WALL, FLOOR, ENTRY, EXIT;
        val value: Int get() = entries.indexOf(this)
    }
}