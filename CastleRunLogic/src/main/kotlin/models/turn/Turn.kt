package org.example.models.turn

import org.example.models.player.Player

class Turn(val player: Player, val number: Int) {
    val actions: MutableList<BaseAction> = mutableListOf()
    val dices: MutableList<Dice> = mutableListOf()

    fun rollDices(player: Player, numberOfDices: Int, isDuel: Boolean) {
        dices.add(Dice(player, numberOfDices, isDuel))
        actions.add(RollDiceAction(player.username, dices.last()))
    }
}