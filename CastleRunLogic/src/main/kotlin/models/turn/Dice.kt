package org.example.models.turn

import org.example.logic.random

class Dice(val id: Identifier, val player: String, numberOfDices: Int) {
    enum class Identifier { Move, Duel, Item }
    val values: Array<Int> = (1..numberOfDices).map { random(1, 6) }.toTypedArray()
}