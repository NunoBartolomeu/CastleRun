package org.example.models

import org.example.models.board.position.Position

fun Game.useSword(at: Position) {
    val tiles = board.getTile(at)?.let { board.getAllTilesAt(tile = it, distance = 1) }
    tiles?.forEach {
        val piece = getPieceAt(it.position)
        if(piece != null) killPiece(piece)
    }
}
