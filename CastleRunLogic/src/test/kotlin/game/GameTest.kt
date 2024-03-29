package game

import org.example.logic.boardFromFile
import org.example.models.Game
import org.example.models.GameRules
import org.example.models.board.position.Position
import org.example.models.player.Piece
import org.example.models.player.Player
import org.example.models.turn.Dice
import org.example.models.turn.Turn
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

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

        game = Game(board = boardFromFile(boardFile), players = players, rules = GameRules())
        //game.printGame()
    }

    @Test
    fun deployTest() {
        val dice = Dice(p1, 1, false)
        dice.values[0] = 1

        val turn = Turn(p1, 1)
        turn.dices.add(dice)
        game.turns.add(turn)
        game.currTurn.dices.add(dice)

        val position = Position(3, 4)
        game.deploy(dice, p1, position)
        //game.printGame()

        assertEquals(position, game.players[0].pieces[0].position)
    }

    @Test
    fun challengeTest() {
        val dice = Dice(p1, 1, false)
        dice.values[0] = game.challengeNumber

        val turn = Turn(p1, 1)
        turn.dices.add(dice)
        game.turns.add(turn)
        game.currTurn.dices.add(dice)

        val piece = Piece(p1.username, Position(3, 4))
        val enemyPiece = Piece(p2.username, Position(5, 4))

        p1.pieces.add(piece)
        p2.pieces.add(enemyPiece)

        game.challenge(dice, p1, piece.position, p2, enemyPiece.position)
        //game.printGame()

        print(game.currTurn.actions[0].message)

        assert(
            (p1.pieces.isEmpty() ||
                    p2.pieces.isEmpty()) &&
                    !(p1.pieces.isEmpty() && p2.pieces.isEmpty())
        )
    }
}