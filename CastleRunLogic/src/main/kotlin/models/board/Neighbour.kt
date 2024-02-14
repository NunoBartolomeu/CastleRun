package models.board

import org.example.models.board.Direction
import org.example.models.board.Tile

data class Neighbour(val tile: Tile, val direction: Direction)
