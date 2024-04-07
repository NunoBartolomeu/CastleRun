package org.example.models.turn

import org.example.models.player.Player

data class Turn(
    val number: Int,
    val playerUsername: String,
    val dices: MutableList<Dice> = mutableListOf(),
    val actions: MutableList<BaseAction> = mutableListOf(),
) {
    fun hasUnusedDice(): Boolean {
        return dices.any { !it.used }
    }
    fun hasUnusedDiceWithValue(value: Int): Boolean {
        return dices.any { it.value == value && !it.used }
    }
    fun useDice(value: Int) {
        if (!hasUnusedDiceWithValue(value))
            throw Exception("There's no unused dice with the value $value")
        else
            dices.find { it.value == value && !it.used }!!.used = true
    }
    fun isPlayersTurn(player: Player): Boolean {
        return player.username == playerUsername
    }
}