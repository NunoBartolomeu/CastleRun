package org.example.logic

import org.example.logger.ActionLogger
import org.example.logger.Logger
import org.example.models.Game
import org.example.models.board.Direction
import org.example.models.board.Position
import org.example.models.board.Tile
import org.example.models.player.Item
import org.example.models.player.Piece
import org.example.models.player.Player
import org.example.models.turn.Dice

/**
 * Contains a way to move and deploy pieces
 * Contains turn management functions
 */
class GameLogic(val game: Game, logger: Logger) {
    //private val validator = GameValidator(game)
    val logger = ActionLogger(logger)

    fun deploy(username: String, entry: Position, type: Piece.Type) {
        //validator.isValidDeploy(player, entry)

        //Get the player
        val player = game.getPlayer(username)

        //Create a new piece
        val piece = Piece(player.username, entry, type)

        //Add the piece to the player
        player.pieces.add(piece)

        logger.logDeploy(player.username, type.name, entry, player.color)
    }

    fun move(piece: Piece, path: List<Position>) {
        //validator.isValidMove(piece, direction)

        //Move the piece one step at a time
        path.forEach { p ->
            //Update the path
            piece.path.add(p)

            //Check for duels
            val neighbors = p.getNeighbours(Direction.all)
            neighbors.forEach { neighbor ->
                val tile = game.board[neighbor]
                if (tile != null && tile.type != Tile.Type.ENTRY && tile.type != Tile.Type.EXIT) {
                    val enemy = game.getPieceAt(neighbor)
                    if (enemy != null)
                        duel(piece, enemy)
                }
            }
        }

        logger.logMove(piece.owner, piece.type.name, piece.position, piece.path.last())

        piece.position = piece.path.last()
    }

    fun damagePiece(piece: Piece, damage: Int) {
        //validator.isValidDamage(player, piece)
        
        //Damage the armor
        piece.stats.defense -= damage
        
        //If the armor breaks, damage the health
        if (piece.stats.defense < 0) {
            piece.stats.health += piece.stats.defense
            piece.stats.defense = 0
        }

        //If the health is 0, kill the piece
        if (piece.stats.health <= 0)
            killPiece(piece)

        logger.logDamage(piece.type.name, damage)
    }

    private fun killPiece(piece: Piece) {
        //validator.isValidKill(game, player, piece)

        //Remove the piece
        game.getPlayer(piece.owner).pieces.remove(piece)

        logger.logKill(piece.type.name)
    }

    //The pieces fight each other to the death taking turns, in each turn the pieces roll a die to add to their attack, the allie attacks first every turn
    fun duel(ally: Piece, enemy: Piece) {
        //validator.isValidDuel(ally, enemy)

        //Get the owners of the pieces
        val allyPlayer = game.getPlayer(ally.owner)
        val enemyPlayer = game.getPlayer(enemy.owner)

        //Create the dices
        val allyDice = Dice.sixSided()
        val enemyDice = Dice.sixSided()

        while (ally.stats.health > 0 && enemy.stats.health > 0) {
            //Roll the dices
            allyDice.roll()
            enemyDice.roll()

            //Log the rolls
            logger.logDiceRoll(ally.type.name, allyDice.currValue, allyPlayer.color)
            logger.logDiceRoll(enemy.type.name, enemyDice.currValue, enemyPlayer.color)

            val allyAttack = ally.stats.attack + allyDice.currValue
            val enemyAttack = enemy.stats.attack + enemyDice.currValue

            //The enemy takes damage
            damagePiece(enemy, allyAttack)

            //If the enemy is still alive, it attacks
            if (enemy.stats.health > 0)
                damagePiece(ally, enemyAttack)
        }

        logger.logDuel(ally.type.name, enemy.type.name)
    }

    fun addItem(player: Player, item: Item) {
        //validator.isValidAddItem(player, item)

        //Add the item to the player
        player.items.add(item)

        logger.logAddItem(player.username, item.type.name, player.color)
    }

    fun giveItem(player: Player, item: Item, piece: Piece) {
        //validator.isValidGiveItem(player, item, piece)

        //Give the item to the piece
        piece.items.add(item)

        //Remove the item from the player
        removeItem(player, item)

        logger.logGiveItem(player.username, item.type.name, piece.type.name, player.color)
    }

    fun activateItem(piece: Piece, item: Item) {
        //validator.isValidActivateItem(piece, item)

        val player = game.getPlayer(piece.owner)

        //Activate the item
        //TODO: Implement item effects

        //Remove the item from the piece
        removeItem(piece, item)

        logger.logActivateItem(piece.type.name, item.type.name, player.color)
    }

    fun removeItem(piece: Piece, item: Item) {
        //validator.isValidRemoveItem(piece, item)

        val player = game.getPlayer(piece.owner)

        //Remove the item from the piece
        piece.items.remove(item)

        logger.logRemoveItem(piece.type.name, item.type.name, player.color)
    }

    fun removeItem(player: Player, item: Item) {
        //validator.isValidRemoveItem(player, item)

        //Remove the item from the player
        player.items.remove(item)

        logger.logRemoveItem(player.username, item.type.name, player.color)
    }
}
