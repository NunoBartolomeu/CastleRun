package org.example.models.turn

data class Round(
    val number: Int,
    val playersUsernames: MutableList<String>
) {
}
