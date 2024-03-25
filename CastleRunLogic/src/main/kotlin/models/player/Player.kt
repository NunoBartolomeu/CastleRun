package org.example.models.player

import org.example.models.board.position.Position

data class Player(val username: String) {
    val pieces: MutableList<Piece> = mutableListOf()
    private val items: MutableList<Item> = mutableListOf()
    var piecesAtExit = 0

    fun getPieceAt(position: Position): Piece? {
        return pieces.find { it.position == position }
    }

    fun movePiece(from: Position, to: Position) {
        val piece = getPieceAt(from)
        if (piece != null) {
            piece.position = to
        }
    }

    fun deployPiece(at: Position) {
        pieces.add(Piece(username, at))
    }

    fun killPiece(at: Position) {
        val piece = getPieceAt(at)
        if (piece != null) {
            pieces.remove(piece)
        }
    }

    fun useItem(piece: Piece) {

    }

    fun addItem(item: Item) {
        items.add(item)
    }

    fun pieceReachedExit(piece: Piece) {
        piecesAtExit++
    }

    fun pieceKilledAtExit(piece: Piece) {
        if(piecesAtExit >= 0) piecesAtExit--
        else println("$username almost had -1 pieces.")
    }
}