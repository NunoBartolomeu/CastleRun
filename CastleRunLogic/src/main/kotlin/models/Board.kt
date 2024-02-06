package org.example.models

data class Board(val tiles: Array<Array<Tile>>, val equipment: Map<Coords, Equipment>? ) {
    //TODO make a simple 9 por 18 board
    //TODO set the entrances list and the exits list
    val entries: MutableList<Coords> = mutableListOf()
    val exits : MutableList<Coords> = mutableListOf()

    init {
        //run through the tiles and save all entries to the list

        //Por cada row fazer,
        tiles.forEachIndexed { rowIdx, row ->
            //Por cada col fazer,

            row.forEachIndexed { colIdx, col ->
                //Se o tile == Entry

                if (tiles[rowIdx][colIdx] == Tile.Entry)
                //Guardar tile entry
                    entries.add(Coords(rowIdx, colIdx))

                if (tiles[rowIdx][colIdx] == Tile.Exit)
                //Guardar tile exit
                    exits.add(Coords(rowIdx, colIdx))
            }
        }
    }
    fun visualization() {
        for (row in this.tiles) {
            for (tile in row) {
                print(when (tile) {
                    Tile.Wall ->"1"
                    Tile.Floor ->"2"
                    Tile.Entry ->"3"
                    Tile.Exit ->"4"
                })
            }
            println()
        }
    }



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

