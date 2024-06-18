package models.variables

/**
 * Contains all the possible variables a game can have.
 * Doesn't include Item, Duel or Challenge variables.
 * */
data class GameVariables (
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
    val piecesHp: Int = 1,

    //Dices
    val numDicesToMove: Int = 2,
    val minDiceValue: Int = 1,
    val maxDiceValue: Int = 6
)
