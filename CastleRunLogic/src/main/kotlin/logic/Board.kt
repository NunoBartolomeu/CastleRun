package org.example.logic

import org.example.models.Board
import org.example.models.Coords
import org.example.models.Equipment

fun newBoard(numRows: Int, numCols: Int, entries: List<Coords>, exits: List<Coords>, walls: List<Coords>, equipment: Map<Coords, Equipment>): Board {
    val tiles = Board.getTiles(numRows, numCols, walls, entries, exits)
    return Board(tiles, entries, exits, equipment)
}

fun newBoardWithRandomEquipment(numRows: Int, numCols: Int, entries: List<Coords>, exits: List<Coords>, walls: List<Coords>, percentageOfEquipment: Int): Board {
    val tiles = Board.getTiles(numRows, numCols, walls, entries, exits)
    val equipment = Board.randomEquipment(tiles, percentageOfEquipment)
    return Board(tiles, entries, exits, equipment)
}