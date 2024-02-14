package org.example.logic

class Multi21 {
    //Generate a board using the algorithm Multi21
    fun generateBoard(numRows: Int, numCols:Int, entries: List<Coords>, exits: List<Coords>) {
        //Create a void board
        val mutableBoard = MutableBoard(numRows, numCols)

        //Fill the outer walls
        mutableBoard.fillOuterWalls()

        //Set the entries and exits, should change later but for now just make 1/3 of the number of rows entries and 1/3 exits
        mutableBoard.setRandomEntriesAndExits(numRows/3, numRows/3)

        //Set the
    }
}