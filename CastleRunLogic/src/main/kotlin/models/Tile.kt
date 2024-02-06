package org.example.models

data class Tile(val coords: Coords, val type: Type) {
    enum class Type { Void, Wall, Floor, Entry, Exit;
        fun getValue(): Int {
            return when(this) {
                Void -> 0
                Wall -> 1
                Floor -> 2
                Entry -> 3
                Exit -> 4
            }
        }
        fun printTile() {
            print(when(this) {
                Void -> " "
                Wall -> "1"
                Floor -> "2"
                Entry -> "3"
                Exit -> "4"
            })
        }
    }
}