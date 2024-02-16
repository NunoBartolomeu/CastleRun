package org.example.models.turn

data class Event(val type: Type, val info: Any) {
    enum class Type {
        RollDice,
        Move,
        Duel,
        Item,
        Kill,
        }
    }