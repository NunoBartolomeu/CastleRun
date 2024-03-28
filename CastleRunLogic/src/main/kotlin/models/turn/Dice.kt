package org.example.models.turn

import org.example.logic.random
import org.example.models.player.Player

class Dice(val player: Player, numberOfDices: Int, val isDuel: Boolean) {
    val values: Array<Int> = (1..numberOfDices).map {0}.toTypedArray()
    val wasRolled: Boolean get() = values.all { it != 0 }
    val used: Array<Boolean> = (1..numberOfDices).map {false}.toTypedArray()

    fun roll() {
        values.indices.forEach { values[it] = random(1, 6) }
    }

    fun use(index: Int) {
        used[index] = true
    }
}