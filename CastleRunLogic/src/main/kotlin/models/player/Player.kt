package org.example.models.player

data class Player(val username: String) {
    val pieces: MutableList<Piece> = mutableListOf()
    private val items: MutableList<Item> = mutableListOf()
    val piecesAtExit: Int get() = pieces.size

    override fun equals(other: Any?): Boolean {
        if (other != null) {
            val otherPlayer = other as Player
            return otherPlayer.username == this.username && otherPlayer.pieces == this.pieces && otherPlayer.items == this.items && otherPlayer.piecesAtExit == this.piecesAtExit
        }
        return false
    }
}