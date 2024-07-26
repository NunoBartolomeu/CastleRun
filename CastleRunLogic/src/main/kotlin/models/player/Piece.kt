package org.example.models.player

import org.example.models.board.Position

data class Piece(val owner: String, var position: Position, val type: Type) {
    enum class Type { Pesant, Warrior, Thief, Knight, King}
    data class Stats(var health: Int, var attack: Int, var defense: Int, var speed: Int)
    
    val stats: Stats = getStats(type)
    val items: MutableList<Item> = mutableListOf()
    val path: MutableList<Position> = mutableListOf(position)

    companion object {
        fun getStats(type: Type): Stats {
            return when (type) {
                Type.Pesant ->  Stats(10, 0, 0, 0)
                Type.Warrior -> Stats(16, 2, 2, 0)
                Type.Thief ->   Stats(8, 1, 0, 2)
                Type.Knight ->  Stats(14, 0, 2, 4)
                Type.King ->    Stats(10, 4, 4, 0)
            }
        }
    }
    
    fun getHealth(): Int = stats.health
    fun getDamage(): Int = items.sumBy { it.offense } + stats.attack
    fun getDefense(): Int = items.sumBy { it.defense } + stats.defense
    fun getSpeed(): Int = items.sumBy { it.speed } + stats.speed
}