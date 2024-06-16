package org.example.models.turn

import utility.random

class Dice(min: Int, max: Int, val value: Int = random(min, max), var used: Boolean = false)
