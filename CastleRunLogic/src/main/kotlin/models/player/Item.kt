package org.example.models.player

data class Item(val type: Type, val offense: Int, val defense: Int, val speed: Int) {
    enum class Type { Sword, Axe, Shield, Bow, Boots, Cape, Dagger, Robe, Chestplate, Rope , Helm , Goat }
    data class Stats(var offense: Int, var defense: Int, var speed: Int)

    val stats: Stats = getStats(type)

    companion object {
        fun getStats(type: Type): Stats {
            return when (type) {
                Type.Sword ->  Stats(4, 0, 0)
                Type.Axe ->    Stats(6, -2, 0)
                Type.Shield -> Stats(0, 4, 0)
                Type.Bow ->    Stats(3, 0, 1)
                Type.Boots ->  Stats(0, 0, 2)
                Type.Cape ->   Stats(0, 2, 1)
                Type.Dagger -> Stats(2, 0, 2)
                Type.Robe ->   Stats(0, 2, 2)
                Type.Chestplate -> Stats(0, 4, -1)
                Type.Rope ->   Stats(0, 0, 3)
                Type.Helm ->   Stats(0, 2, 0)
                Type.Goat ->   Stats(0, 0, 0)
            }
        }
    }
}