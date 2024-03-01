package org.example.models.turn

import org.example.models.player.Item
import org.example.models.player.Piece
import org.example.models.board.position.Position

sealed class BaseAction(val message: String)

class RollDiceAction(val player: String, val dice: Dice): 
    BaseAction("The $player rolled the dice ${dice.id} and got ${dice.values}")
class MoveAction(val player: String, val from: Position, val to: Position):
    BaseAction("The $player moved the piece from $from to $to")
class DeployAction(val player: String, val at: Position):
    BaseAction("The $player deployed a piece at $at")
class UseItemAction(val player: String, val item: Item, val piece: Piece):
    BaseAction("The $player used the item $item on the piece $piece")
class ChosenRewardAction(val reward: String):
    BaseAction("The system chose the reward $reward")
class ChosenPunishmentAction(val punishment: String):
    BaseAction("The system chose the punishment $punishment")
class DuelAction(val player: String, val allyPiece: Piece, val enemyPiece: Piece, val number: Int):
    BaseAction("The $player's piece $allyPiece is dueling the piece $enemyPiece with $number")
class KillAction(val player: String, val piece: Piece):
    BaseAction("The $player killed the piece $piece")
class EndTurnAction(val player: String):
    BaseAction("The $player ended the turn")
class EndGameAction(val player: String):
    BaseAction("The $player ended the game")
