package org.example.models.board

data class Tile(val position: Pos, val type: Type) {
    enum class Type { 
        ROCK,
        BUSH,
        FOG,
        WINDOW,
        VINE,
        FENCE,
        DIRT,
        GRASS,
        
        VOID,
        ENTRY,
        EXIT,

        WATERFALL,
        MINE,
        ARMORY,
        CAMP,
    }

    data class Info(val canPassThrough: Boolean, val canSeeThrough: Boolean, val canBurn: Boolean)

    val info: Info = getInfo(type)

    companion object {
        fun getInfo(type: Type): Info {
            return when (type) {
                Type.ROCK -> Info(false, false, false)
                Type.BUSH -> Info(false, false, true)
                Type.FOG -> Info(true, false, false)
                Type.WINDOW -> Info(false, true, false)
                Type.VINE -> Info(true, false, true)
                Type.FENCE -> Info(false, false, true)
                Type.DIRT -> Info(true, true, false)
                Type.GRASS -> Info(true, true, true)

                Type.VOID -> Info(false, false, false)
                Type.ENTRY -> Info(true, true, false)
                Type.EXIT -> Info(true, true, false)

                Type.WATERFALL -> Info(true, false, false)
                Type.MINE -> Info(true, false, false)
                Type.ARMORY -> Info(true, false, false)
                Type.CAMP -> Info(true, false, false)
            }
        }
    }
}