package org.example.models.turn

typealias DiceValue = Dice.DiceValue

class Dice(private val possibleValues: Array<Int>) {
    data class DiceValue(val value: Int, var used: Boolean = false)
    
    var currValue = 0
    
    fun roll() {
        currValue = possibleValues.random()
    }

    fun toDiceValue(): DiceValue {
        this.roll()
        return DiceValue(currValue)
    }

    companion object {
        fun sixSided() = Dice(arrayOf(1,2,3,4,5,6))
    }
}
