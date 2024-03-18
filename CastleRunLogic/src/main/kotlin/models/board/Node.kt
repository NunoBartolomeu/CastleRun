data class Node(
    val position: Position,
    val previous: Node?,
    val next: MutableList<Node>,
    val distance: Int,
)