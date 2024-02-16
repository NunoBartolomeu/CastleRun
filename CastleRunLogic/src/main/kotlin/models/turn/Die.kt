package org.example.models.turn

import org.example.logic.random

class Die(val owner: String) {
    var value: Int? = null
    fun roll() { value = random(1, 6) }
}