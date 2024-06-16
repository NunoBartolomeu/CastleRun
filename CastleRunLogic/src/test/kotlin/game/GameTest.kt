package game

import org.example.logic.boardFromFile
import org.example.models.Game
import org.example.models.board.Position
import org.example.models.player.Item
import org.example.models.player.Piece
import org.example.models.player.Player
import org.example.models.turn.Dice
import org.example.models.turn.Turn
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File
import java.util.UUID
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GameTest {
    private lateinit var game: Game
    private lateinit var p1: Player
    private lateinit var p2: Player

    @BeforeEach
    fun createGame() {
        val boardFile = File("src/test/resources/test_board.txt")

        p1 = Player("Player 1")
        p2 = Player("Player 2")
        val players = listOf(p1, p2)

        game = Game(id = UUID.randomUUID().toString(), board = boardFromFile(boardFile), players = players)
        //game.printGame()
    }

    @Test
    fun deployTest() {
        val dice = Dice(1, 6, 1)

        val turn = Turn(1, p1.username, mutableListOf(dice))
        game.turns.add(turn)

        val position = Position(3, 4)
        val entry = Position(3, 3)
        game.deploy(p1, entry, position, dice.value)
        //game.printGame()

        assertEquals(position, game.players[0].pieces[0].position)
    }

    @Test
    fun `Deploy a piece with wrong distance`() {

    }

    @Test
    fun `Deploy a piece in a position of invalid distance`() {

    }

    @Test
    fun `Deploy a piece on top of another piece`() {

    }

    @Test
    fun `Deploy a piece on a wall`() {

    }

    @Test
    fun `Deploy a piece on floor after a wall`() {

    }

    @Test
    fun movePiece() {
        val dice = Dice(1, 6, 1)

        val turn = Turn(1, p1.username, mutableListOf(dice))
        game.turns.add(turn)

        val to = Position(3, 5)
        val from = Position(3, 4)
        game.move(p1, from, to, dice.value)
        //game.printGame()

        assertEquals(to, game.players[0].pieces[0].position)
    }

    @Test
    fun `Move a piece with wrong distance`() {

    }

    @Test
    fun `Move a piece in a position of invalid distance`() {

    }

    @Test
    fun `Move a piece on top of another`() {

    }

    @Test
    fun `Move a piece to a wall`() {

    }

    @Test
    fun `Move a piece on floor after a wall`() {

    }

    @Test
    fun swordTest() {
        val turn = Turn(1, p1.username)
        game.turns.add(turn)

        val position = Position(3, 4)
        val piece = Piece(p1.username, position)
        val enemyPiece1 = Piece(p2.username, Position(3, 5))
        val enemyPiece2 = Piece(p2.username, Position(4, 3))

        p1.pieces.add(piece)
        p2.pieces.add(enemyPiece1)
        p2.pieces.add(enemyPiece2)

        val sword = Item(Item.Type.Sword)
        p1.items.add(sword)

        game.useItem(p1, sword, position)

        assertTrue(p2.pieces.isEmpty())
    }

    /*
    @Test
    fun challengeTest() {
        val dice = Dice(1, 6, game.challengeNumber)

        val turn = Turn(1, p1.username, mutableListOf(dice))
        game.turns.add(turn)

        val piece = Piece(p1.username, Position(3, 4))
        val enemyPiece = Piece(p2.username, Position(5, 4))

        p1.pieces.add(piece)
        p2.pieces.add(enemyPiece)

        //game.challenge(dices, p1, piece.position, p2, enemyPiece.position)
        //game.printGame()

        //print(game.currTurn.actions[0].message)

        assert(
            (p1.pieces.isEmpty() ||
                    p2.pieces.isEmpty()) &&
                    !(p1.pieces.isEmpty() && p2.pieces.isEmpty())
        )
    }*/
}