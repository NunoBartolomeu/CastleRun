package org.example.models

data class GameRules (
    //Entries    
    val sharedEntries: Boolean = true,

    //Exits
    val numExitsToWin: Int = 1,
    val perfectExit: Boolean = false,
    val lockedExits: Boolean = false,
    val onlyOneWinner: Boolean = true,

    //Pieces
    val maxPieces: Int = 7,
    val maxActivePieces: Int = 3,

    //Dices
    val numDicesToMove: Int = 2,
    val minDiceValue: Int = 1,
    val maxDiceValue: Int = 6,

    //Items
    val swordRange: Int = 1
)
