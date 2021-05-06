package server.engine

import common.model.*
import common.protocol.commands.Direction

sealed class GameAction {
    open fun getName() = this.javaClass.name

    abstract fun execute(world: World)
}

class CreatePlayer(val id: Int, posX: Int = 2, posY: Int = 2) : GameAction() {
    override fun execute(world: World) {
        val player = Player(2, 2, 100, id)
        world.players[id] = player
    }
}

class CreateBot(val id: Int) : GameAction() {
    override fun execute(world: World) {
        val randomForCoord = java.util.Random()
        var x = randomForCoord.ints(1, 2, world.map.sizeX).sum()
        var y = randomForCoord.ints(1, 2, world.map.sizeY).sum()
        while (world.map.stones.any { it.x == x && it.y == y } || (x == 2 && y == 2)) {
            x = randomForCoord.ints(1, 2, world.map.sizeX).sum()
            y = randomForCoord.ints(1, 2, world.map.sizeY).sum()
        }
        val randomForChooseKind = java.util.Random()
        val r = randomForChooseKind.ints(1, 0, 100).sum()
        if (r <= 40) {
            world.players[id] = PassiveAngryBot(x, y, 100, 10, id)
        } else {
            world.players[id] = ActiveAngryBot(x, y, 100, 10, id)
        }
    }
}


class Move(private val playerID: Int, private val direction: Direction) : GameAction() {
    override fun getName(): String {
        return "move"
    }

    override fun execute(world: World) {
        val gameObject: MovableGameObject = world.players[playerID] ?: return

        var x = gameObject.x
        var y = gameObject.y
        when (direction) {
            Direction.LEFT -> x -= 1
            Direction.RIGHT -> x += 1
            Direction.UP -> y += 1
            Direction.DOWN -> y -= 1
        }

        // TODO: Improve game object to stop this trash
        if (!world.map.stones.any { it.x == x && it.y == y }) {
            gameObject.move(x, y)
        }
    }
}

class Shoot : GameAction() {

    override fun getName(): String {
        return "shoot"
    }

    override fun execute(currWorld: World) {
        TODO("Not yet implemented")
    }
}

class Exit : GameAction() {
    override fun getName(): String {
        return "exit"
    }

    override fun execute(currWorld: World) {
        TODO("Not yet implemented")
    }
}
