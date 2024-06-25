package org.example.models.turn

typealias DiceValue = Dice.DiceValue

class Dice(private val values: Array<Int>) {
    data class DiceValue(val value: Int, var used: Boolean = false)
    fun roll(): DiceValue = DiceValue(values.random())
}
