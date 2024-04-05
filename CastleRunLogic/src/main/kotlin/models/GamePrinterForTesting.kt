package org.example.models

import org.example.models.board.Tile

fun printGame(game: Game, useEmojis: Boolean = true) {
    println("Game: ${game.id}")
    println("Players: ${game.players.joinToString(", ") { it.username }}")
    println()

    //Print column numbers
    print("\uD83D\uDD38")
    for (col in 0 until game.board.numCols) {
        print("${col%10}\uFE0F⃣")
    }
    println()

    for (row in 0 until game.board.numRows) {
        //Print row numbers
        print("${row%10}\uFE0F⃣")

        for (col in 0 until game.board.numCols) {
            val tile = game.board.tiles[row][col]
            if (useEmojis)
                if (game.players.isNotEmpty() && game.players.any { it.pieces.any { it.position == tile.position } }) {
                    printPiece(game.players.indexOfFirst { it.pieces.any { it.position == tile.position } })
                }
                else printTile(tile)
            else
                print("${tile.type.value}")
        }
        println()
    }
}

private fun printTile(tile: Tile) {
    when(tile.type) {
        Tile.Type.ENTRY -> print("\uD83C\uDD70\uFE0F")
        Tile.Type.EXIT -> print("\uD83C\uDD71\uFE0F")
        Tile.Type.WALL -> print("⬛")
        Tile.Type.FLOOR -> print("\u2B1C")
        Tile.Type.VOID -> print("\uD83D\uDD73\uFE0F")
    }
}

private fun printPiece(playerIdx: Int) {
    when(playerIdx) {
        0 -> print("\uD83D\uDFE1")
        1 -> print("\uD83D\uDFE2")
        2 -> print("\uD83D\uDD35")
        3 -> print("\uD83D\uDFE0")
        else -> error("More than 4 players are not supported")
    }
}