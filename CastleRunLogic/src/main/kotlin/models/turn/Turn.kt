package org.example.models.turn

class Turn(val player: String, val number: Int) {
    val actions: MutableList<BaseAction> = mutableListOf()
    val dices: MutableList<Dice> = mutableListOf()
}