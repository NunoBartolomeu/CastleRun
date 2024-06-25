package org.example.models.turn

data class Turn(
    val number: Int,
    val playerUsername: String,
    val diceValues: List<DiceValue>,
) {
    val actions: MutableList<BaseAction> = mutableListOf()
}
