package org.example.models.turn

class Turn(val player: String, val number: Int) {
    val actions: MutableList<BaseAction> = mutableListOf()
    val dices: MutableList<Dice> = mutableListOf()

    fun rollDices(player: String, numberOfDices: Int, isDuel: Boolean) {
        dices.add(Dice(player, numberOfDices, isDuel))
        actions.add(RollDiceAction(player, dices.last()))
    }
}