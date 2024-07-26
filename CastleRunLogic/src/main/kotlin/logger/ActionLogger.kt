package org.example.logger

import org.example.models.board.Position

/**
 * Contains the actions that can be logged
 * The actions are:
 * - Deploy: The player $player deployed the piece $piece at $position.
 * - Move: The player $player moved the piece $piece from $from to $to.
 * - Damage: The piece $piece received $damage damage.
 * - Kill: The piece $piece was killed.
 * - Duel: The piece $piece1 started a duel with the piece $piece2.
 */

class ActionLogger(private val logger: Logger) {
    private fun log(entity: String, message: String, color: RGB?) {
        if (color != null) {
            logger.log(entity, message, color)
        } else {
            logger.log(entity, message)
        }
    }

    // Game actions //

    fun logDeploy(player: String, piece: String, position: Position, color: RGB? = null) {
        val message = "Deployed the piece $piece at $position."
        log(player, message, color)
    }

    fun logMove(player: String, piece: String, from: Position, to: Position, color: RGB? = null) {
        val message = "Moved the piece $piece from $from to $to."
        log(player, message, color)
    }

    fun logDamage(piece: String, damage: Int, color: RGB? = null) {
        val message = "Received $damage damage."
        log(piece, message, color)
    }

    fun logKill(piece: String, color: RGB? = null) {
        val message = "Was killed."
        log(piece, message, color)
    }

    fun logDuel(piece1: String, piece2: String, color: RGB? = null) {
        val message = "Started a duel with the piece $piece2."
        log(piece1, message, color)
    }

    fun logDiceRoll(entity: String, dice: Int, color: RGB? = null) {
        val message = "Rolled a $dice."
        log(entity, message, color)
    }

    fun logAddItem(player: String, item: String, color: RGB? = null) {
        val message = "Added the item $item."
        log(player, message, color)
    }

    fun logGiveItem(player: String, item: String, receiver: String, color: RGB? = null) {
        val message = "Gave the item $item to $receiver."
        log(player, message, color)
    }

    fun logActivateItem(player: String, item: String, color: RGB? = null) {
        val message = "Activated the item $item."
        log(player, message, color)
    }

    fun logRemoveItem(entity: String, item: String, color: RGB? = null) {
        val message = "Removed the item $item."
        log(entity, message, color)
    }

    // Turn actions //

    fun logStartGame(entity: String, id: String, color: RGB? = null) {
        val message = "Started the game with id $id."
        log(entity, message, color)
    }

    fun logEndGame(entity: String, winner: String, color: RGB? = null) {
        val message = "Ended the game. The winner is $winner."
        log(entity, message, color)
    }

    fun logStartRound(entity: String, number: Int, color: RGB? = null) {
        val message = "Started round $number."
        log(entity, message, color)
    }

    fun logEndRound(entity: String, number: Int, color: RGB? = null) {
        val message = "Ended round $number."
        log(entity, message, color)
    }

    fun logStartTurn(entity: String, number: Int, color: RGB? = null) {
        val message = "Started turn $number."
        log(entity, message, color)
    }

    fun logEndTurn(entity: String, number: Int, color: RGB? = null) {
        val message = "Ended turn $number."
        log(entity, message, color)
    }
}

