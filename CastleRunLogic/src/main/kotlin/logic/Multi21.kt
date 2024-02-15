package org.example.logic

import org.example.models.board.*
import org.example.random

class Multi21 {
    //Generate a board using the algorithm Multi21
    fun generateBoard(numRows: Int, numCols:Int): Board {
        //Create a void board
        val mutableBoard = MutableBoard(numRows, numCols)

        //Fill the outer walls
        mutableBoard.fillOuterWalls()

        //Set the entries and exits, should change later but for now just make 1/3 of the number of rows entries and 1/3 exits
        mutableBoard.setRandomEntriesAndExits(1, 1)

        //Set the floor and walls by the Multi21 algorithm
        val currGen = mutableListOf<Tile>()
        mutableBoard.getEntriesAndExits().forEach { tile ->
            currGen.add(tile)
        }

        while (currGen.isNotEmpty()) {
            mutableBoard.print()
            println()
            val nextGen = mutableListOf<Tile>()
            currGen.forEach { tile ->
                mutableBoard.getNeighbors(tile).forEach { neighbour ->
                    if (neighbour.tile.type == Tile.Type.VOID) {
                        random(1, 2).let {
                            if (it == 1) 
                                mutableBoard.changeTile(neighbour.tile.position, Tile.Type.FLOOR)
                            else
                                mutableBoard.changeTile(neighbour.tile.position, Tile.Type.WALL)
                                nextGen.add(neighbour.tile)
                        }
                    }
                }

                val north = mutableBoard.getNeighbor(tile, Direction.North)
                val south = mutableBoard.getNeighbor(tile, Direction.South)
                val east = mutableBoard.getNeighbor(tile, Direction.East)
                val west = mutableBoard.getNeighbor(tile, Direction.West)

                val diagonalNeighbours = mutableBoard.getDiagonalNeighbors(tile)
                diagonalNeighbours.forEach { neighbour ->
                    if (neighbour.tile.type == Tile.Type.VOID) {
                        when(neighbour.direction) {
                            Direction.NorthEast -> {
                                if (north?.tile?.type == Tile.Type.FLOOR && east?.tile?.type == Tile.Type.FLOOR) {
                                    mutableBoard.changeTile(neighbour.tile.position, Tile.Type.WALL)
                                }
                            }
                            Direction.NorthWest -> {
                                if (north?.tile?.type == Tile.Type.FLOOR && west?.tile?.type == Tile.Type.FLOOR) {
                                    mutableBoard.changeTile(neighbour.tile.position, Tile.Type.WALL)
                                }
                            }
                            Direction.SouthEast -> {
                                if (south?.tile?.type == Tile.Type.FLOOR && east?.tile?.type == Tile.Type.FLOOR) {
                                    mutableBoard.changeTile(neighbour.tile.position, Tile.Type.WALL)
                                }
                            }
                            Direction.SouthWest -> {
                                if (south?.tile?.type == Tile.Type.FLOOR && west?.tile?.type == Tile.Type.FLOOR) {
                                    mutableBoard.changeTile(neighbour.tile.position, Tile.Type.WALL)
                                }
                            }
                            else -> { error("Diagonal neighbour cannot be of direction: ${neighbour.direction}") }    
                        }
                    }
                }
            }

            currGen.clear()
            currGen.addAll(nextGen)
        }

        //Change illegal floors to walls, a floor is illegal if doesn't have at least 2 floor as immediate neighbours
        var changed = false
        while (!changed) {
            changed = false
            mutableBoard.tiles.forEach { row ->
                row.forEach { tile ->
                    if (tile.type == Tile.Type.FLOOR) {
                        val neighbours = mutableBoard.getNeighbors(tile)
                        val numFloorNeighbours = neighbours.count { it.tile.type == Tile.Type.FLOOR }
                        if (numFloorNeighbours < 2) {
                            mutableBoard.changeTile(tile.position, Tile.Type.WALL)
                            changed = true
                        }
                    }
                }
            }
        }

        //Clean-up by changing walls, that are surrounded by walls, to void
        changed = false
        while (!changed) {
            changed = false
            mutableBoard.tiles.forEach { row ->
                row.forEach { tile ->
                    if (tile.type == Tile.Type.WALL) {
                        val neighbours = mutableBoard.getAllNeighbors(tile)
                        val notWalls = neighbours.count { it.tile.type.value() > Tile.Type.WALL.value() }
                        if (notWalls == 0) {
                            mutableBoard.changeTile(tile.position, Tile.Type.VOID)
                            changed = true
                        }
                    }
                }
            }
        }

        return mutableBoard.toBoard()
    }
}

fun main() {
    val board = Multi21().generateBoard(5, 12)
    board.print()
}