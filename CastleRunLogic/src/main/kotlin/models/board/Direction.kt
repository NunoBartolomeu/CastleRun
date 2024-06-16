package org.example.models.board

typealias Dir = Direction

enum class Direction { N, E, S, W, NE, NW, SE, SW;
    fun asPosition(): Position {
        return when (this) {
            N -> Position(-1, 0)
            E -> Position(0, 1)
            S -> Position(1, 0)
            W -> Position(0, -1)
            NE -> Position(-1, 1)
            NW -> Position(-1, -1)
            SE -> Position(1, 1)
            SW -> Position(1, -1)
        }
    }

    companion object {
        val cardinal = listOf(N, W, S, E)
        val diagonal = listOf(NE, NW, SE, SW)
        val all = entries
    }
}