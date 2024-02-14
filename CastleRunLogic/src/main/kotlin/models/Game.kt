package org.example.models

import org.example.models.board.Board
import org.example.models.board.Position
import org.example.models.items.Item
import org.example.models.player.Player

data class Game(val board: Board, val itemLayout: Map<Position, Item>, val players: List<Player>, val rules: Rules) {
    data class Rules(
        val numExitsToWin: Int, 
        val perfectExit: Boolean, 
        val lockedExits: Boolean, 
        val maxPieces: Int, 
        val maxActivePieces: Int,
        val randomEquipment: Boolean,
        val randomEquipmentByClass: Boolean,
        val maxEquipmentPerTile: Int,
    )
}
