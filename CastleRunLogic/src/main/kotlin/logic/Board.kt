package org.example.logic

import org.example.toObject
import org.example.models.board.Board
import org.example.models.board.Position
import org.example.models.items.Item
import org.example.toJson
import java.io.File

fun newBoard(numRows: Int, numCols: Int, entries: List<Position>, exits: List<Position>, walls: List<Position>, item: Map<Position, Item>): Board {
    val tiles = Board.getTiles(numRows, numCols, walls, entries, exits)
    return Board(tiles, entries, exits, item)
}

fun newBoardWithRandomEquipment(numRows: Int, numCols: Int, entries: List<Position>, exits: List<Position>, walls: List<Position>, percentageOfEquipment: Int): Board {
    val tiles = Board.getTiles(numRows, numCols, walls, entries, exits)
    val equipment = Board.randomEquipment(tiles, percentageOfEquipment)
    return Board(tiles, entries, exits, equipment)
}

fun boardFromFile(file: File): Board {
    if (!file.isFile)
        throw IllegalArgumentException("Path needs to be a file")
    if (file.extension == ".txt")
        throw IllegalArgumentException("File needs to end in \'.txt\'")

    val json = file.readText()
    return json.toObject()
}

fun boardToFile(board: Board, file: File) {
    val json = board.toJson()
    file.writeText(json)
}