package org.example.models.board

import org.example.models.board.position.Position

class MutableTile(position: Position, override var type: Type): Tile(position, type) {
    constructor(row: Int, col: Int, type: Type) : this(Position(row, col), type)

}