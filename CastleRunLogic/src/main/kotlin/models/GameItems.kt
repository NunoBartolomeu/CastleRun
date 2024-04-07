package org.example.models

import org.example.models.board.position.Position

/**
 * For now extension functions will not be used due to not being able to call
 * private functions from the class being extended (might be used in the future)
 */

/*
internal fun Game.useSword(at: Position) {
    val tiles = board.getTile(at)?.let { board.getAllTilesAt(tile = it, distance = itemRules.swordRange) }
    tiles?.forEach {
        val piece = getPieceAt(it.position)
        if(piece != null) damagePiece(piece, itemRules.swordDamage)
    }
}
*/