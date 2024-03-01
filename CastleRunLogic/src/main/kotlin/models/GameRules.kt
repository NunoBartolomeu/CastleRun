package org.example.models

data class GameRules (
    val numExitsToWin: Int,
    val perfectExit: Boolean,
    val lockedExits: Boolean,
    val maxPieces: Int,
    val maxActivePieces: Int,
    val randomEquipment: Boolean,
    val randomEquipmentByClass: Boolean,
    val onlyOneWinner: Boolean,
)
