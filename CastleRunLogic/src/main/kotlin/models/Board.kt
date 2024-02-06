package org.example.models

data class Board(val tiles: Array<Array<Tile>>, val equipment: Map<Coords, Equipment> ) {
    //TODO make a simple 9 por 18 board
    //TODO set the entrances list and the exits list
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Board

        if (!tiles.contentDeepEquals(other.tiles)) return false
        if (equipment != other.equipment) return false

        return true
    }

    override fun hashCode(): Int {
        var result = tiles.contentDeepHashCode()
        result = 31 * result + equipment.hashCode()
        return result
    }
}
