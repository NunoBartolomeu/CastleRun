package org.example.models.turn

class Dice(val id: Identifier, val players: List<String>, val dicesPerPlayer: Int) {
    enum class Identifier { Starting, Move, Duel, Item }

    val list: List<Die> = setDice()

    private fun setDice(): List<Die> {
        if (players.isEmpty() || dicesPerPlayer < 1) throw IllegalArgumentException()
        return players.flatMap { player -> (1..dicesPerPlayer).map { Die(player) } }
    }

    fun roll(username: String) {
        if (players.contains(username)) {
            list.filter { it.owner == username }.forEach {
                it.roll()
            }
        }
    }
}