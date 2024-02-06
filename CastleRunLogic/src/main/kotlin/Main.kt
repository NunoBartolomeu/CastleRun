package org.example

import org.example.models.Board
import org.example.models.Tile

fun main() {
    val numRows = 5
    val numCols = 9
    val tiles: Array<Array<Tile>> = Array(numRows) { Array(numCols) { Tile.Floor } }
    tiles [0][1] = Tile.Entry
    tiles [4][6] = Tile.Exit
    tiles [4][3] = Tile.Wall
    tiles [3][4] = Tile.Wall
    tiles [0][0] = Tile.Entry
    tiles [3][0] = Tile.Wall
    var board = Board(tiles,null)

    board.visualization()

}
