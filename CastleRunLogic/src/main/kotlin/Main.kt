package org.example

import org.example.models.Board
import org.example.models.Tile

fun main() {
    val map =   "11111111\n" +
                "13222141\n" +
                "12112221\n" +
                "12222141\n" +
                "11111111"
    var board = Board(tiles)

    board.visualization()

}
