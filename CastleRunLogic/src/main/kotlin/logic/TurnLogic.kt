package org.example.logic

import org.example.logger.ActionLogger
import org.example.logger.RGB
import org.example.models.Game
import org.example.models.player.Player
import org.example.models.turn.Round
import org.example.models.turn.Turn

private const val systemName = "System"
private val systemColor = RGB.White

fun GameLogic.startGame() {
    logger.logStartGame(systemName, game.id, systemColor)

    startRound()
}

fun GameLogic.endGame() {
    //validator.isValidEndGame(game)

    //Get the winner
    //TODO: Implement win conditions
    val winner = "No one"

    logger.logEndGame(systemName, winner, systemColor)
}

private fun GameLogic.iaGameOver(): Boolean {
    return false
    //TODO: Implement win conditions
}

fun GameLogic.startRound() {
    val number = game.currRound.number + 1
    val order = game.players.map { it.username }.shuffled().toMutableList()
    game.currRound = Round(number, order)

    logger.logStartRound(systemName, number, systemColor)
}

fun GameLogic.endRound() {
    //validator.isValidEndRound(game)

    logger.logEndRound(systemName, game.currRound.number, systemColor)

    if(iaGameOver())
        endGame()
    else
        startRound()
}

fun GameLogic.startTurn() {
    val number = game.currTurn.number + 1
    val player = game.currRound.order.removeFirst()
    val dices = game.dices.map { it.toDiceValue() }
    game.currTurn = Turn(number, player, dices)

    logger.logStartTurn(systemName, number, systemColor)
}

fun GameLogic.endTurn(player: Player) {
    //validator.isValidEndTurn(game, player)

    logger.logEndTurn(systemName, game.currTurn.number, systemColor)

    if (iaGameOver())
        endGame()
    else if (game.currRound.order.isEmpty())
        endRound()
    else
        startTurn()
}
