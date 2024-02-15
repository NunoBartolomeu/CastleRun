package org.example.models

import org.example.models.board.Board
import org.example.models.board.Tile
import org.example.models.position.Position
import org.example.models.items.Item
import org.example.models.player.Player

data class Game(val board: Board<Tile>, val players: List<Player>, val rules: Rules) {
    data class Rules(
        val numExitsToWin: Int,
        val perfectExit: Boolean, 
        val lockedExits: Boolean, 
        val maxPieces: Int, 
        val maxActivePieces: Int,
        val randomEquipment: Boolean,
        val randomEquipmentByClass: Boolean,
        val onlyOneWinner: Boolean,
    )

    //TODO("The Board is basically complete, maybe adding a few more utility functions and it's a wrap")
    //TODO("The Multi21 needs a lot of work still, basically at step 2 only")
    //TODO("The Pieces need utility functions, and consideration for a MutablePiece can be in place")
    //TODO("The Player needs the utility functions, in this case the player actions, every other change seems unlikely")
    //TODO("The Game itself needs the basic functions of the turn logic, also is likely that a new class Turn might be in place")
    //TODO("The Dice and/or Die classes need to be created and implemented to help in the turn. Also deserves more thought")
    //TODO("The recommendation would be to create the state machine of the game first and then it's implementation due to the complexity requirements")
}
