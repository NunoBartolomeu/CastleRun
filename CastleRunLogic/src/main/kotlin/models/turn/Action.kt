package org.example.models.turn

import org.example.models.player.Item
import org.example.models.player.Piece
import org.example.models.board.position.Position
import org.example.models.player.Player

enum class ActionType { START_TURN, END_TURN, GAME_OVER, DEPLOY, MOVE, SELECT_ALLY, SELECT_ENEMY, CHALLENGE_RESULT, USE_ITEM, KILL }

sealed class BaseAction(val type: ActionType ,val message: String)

class StartTurnAction(val player: Player, turnNumber: Int):
    BaseAction(ActionType.START_TURN, "${player.username} started the turn $turnNumber.")

class EndTurnAction(val player: Player):
    BaseAction(ActionType.END_TURN, "${player.username} ended the turn.")

class GameOverAction(val player: Player):
    BaseAction(ActionType.GAME_OVER, "${player.username} won the game.")

class DeployAction(val player: Player, val at: Position):
    BaseAction(ActionType.DEPLOY, "${player.username} deployed a piece at $at")

class MoveAction(val player: Player, val from: Position, val to: Position):
    BaseAction(ActionType.MOVE, "${player.username} moved the piece from $from to $to")

class SelectAllyAction(val player: Player, val ally: Piece):
    BaseAction(ActionType.SELECT_ALLY, "${player.username} selected the ally $ally for the challenge")

class SelectEnemyAction(val player: Player, val enemy: Piece):
    BaseAction(ActionType.SELECT_ENEMY, "${player.username} selected the enemy $enemy for the challenge")

class ChallengeResultAction(val player: Player, val result: Boolean):
    BaseAction(ActionType.CHALLENGE_RESULT, "${player.username} ${if (result) "won" else "lost"} the challenge")

class UseItemAction(val player: Player, val item: Item, val piece: Piece):
    BaseAction(ActionType.USE_ITEM, "${player.username} used the item $item on the piece $piece")

class KillAction(val player: Player, val piece: Piece):
    BaseAction(ActionType.KILL, "${player.username} killed the piece $piece")