package org.example.logic

import org.example.models.board.*

class Multi21 {
    //Generate a board using the algorithm Multi21
    fun generateBoard(numRows: Int, numCols:Int): Board<Tile> {
        //TODO("Technical difficulties with implementation")
        //Create a void board
        val mutableBoard = MutableBoard(numRows, numCols)

        //Fill the outer walls
        mutableBoard.fillOuterWalls()

        //Set the entries and exits, should change later but for now just make 1/3 of the number of rows entries and 1/3 exits
        mutableBoard.setRandomEntriesAndExits(1, 1)

        //Set the floor and walls by the Multi21 algorithm
        val currGen = mutableListOf<MutableTile>()


        return mutableBoard.toBoard()
    }
}

fun main() {
    val board = Multi21().generateBoard(5, 12)
    board.print()
}