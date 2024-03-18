package org.example.models

data class GameRules (
    val numExitsToWin: Int = 1,
    val perfectExit: Boolean = false,
    val lockedExits: Boolean = false,
    val maxPieces: Int = 7,
    val maxActivePieces: Int = 3,
    val onlyOneWinner: Boolean = true,
    val numDicesToMove: Int = 2,
    val numDicesToDuel: Int = 1,
)
