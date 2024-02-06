package org.example.models

data class Game(val board: Board, val equipmentLayout: Map<Coords, Equipment>, val players: List<Player>)
