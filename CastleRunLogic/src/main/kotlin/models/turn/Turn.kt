package org.example.models.turn

import org.example.models.player.Player

data class Turn(
    val number: Int,
    val playerUsername: String,
    val diceValues: List<DiceValue>,
) {
    val actions: MutableList<BaseAction> = mutableListOf()
}