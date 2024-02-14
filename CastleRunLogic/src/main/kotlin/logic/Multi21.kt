package org.example.logic

class Multi21 {
    //Generate a board using the algorithm Multi21
    fun generateBoard(numRows: Int, numCols:Int, entries: List<Coords>, exits: List<Coords>) {
        //Create tiles, set all entries and exits and the external walls
        val tiles = MutableTiles.create(numRows, numCols)
        tiles.setOuterWalls()
        tiles.setMultiple(entries, TileType.Entry)
        tiles.setMultiple(exits, TileType.Exit)

        val generations: MutableMap<Int, List<Coords>> = mutableMapOf()
        //Add entries and exits to the first generation
        
    }
}