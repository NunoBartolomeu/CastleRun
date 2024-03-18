import org.example.models.board.position.Position

data class Node(
    val position: Position,
    val previous: Position?,
    val next: MutableList<Node>?,
    val distance: Int,
)