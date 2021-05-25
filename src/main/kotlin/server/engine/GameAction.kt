package server.engine

import common.model.*
import common.protocol.commands.Direction
import kotlinx.coroutines.CompletableDeferred

sealed class GameEngineRequest

class GetWorld : GameEngineRequest() {
    private val response = CompletableDeferred<World>()

    fun complete(world: World): Boolean {
        return response.complete(world)
    }

    suspend fun await(): World {
        return response.await()
    }
}

sealed class GameAction : GameEngineRequest() {
    open fun getName() = this.javaClass.name

    abstract fun execute(world: World)
}

class CreatePlayer(val id: Int, private val posX: Int = 2, private val posY: Int = 2) : GameAction() {
    override fun execute(world: World) {
        val player = Player(posX, posY, 100, id)
            world.addPlayer(player)
    }
}

class CreateBot(val id: Int) : GameAction() {
    override fun execute(world: World) {
        val randomForCoord = java.util.Random()
        var x = randomForCoord.ints(1, 2, world.map.sizeX).sum()
        var y = randomForCoord.ints(1, 2, world.map.sizeY).sum()
        while (!world.map.cellIsEmpty(x, y) || (x == 2 && y == 2)) {
            x = randomForCoord.ints(1, 2, world.map.sizeX).sum()
            y = randomForCoord.ints(1, 2, world.map.sizeY).sum()
        }
        val randomForChooseKind = java.util.Random()
        val r = randomForChooseKind.ints(1, 0, 100).sum()
        val newBot: MovableGameObject = if (r <= 40) {
            PassiveAngryBot(x, y, 100, 10, id)
        } else {
            ActiveAngryBot(x, y, 100, 10, id)
        }
        world.addPlayer(newBot)
    }
}

class DeleteMovableObject(val id: Int): GameAction() {
    override fun execute(world: World) {
        world.removePlayerById(id)
    }
}

class Move(private val playerID: Int, private val direction: Direction) : GameAction() {
    override fun getName(): String {
        return "move"
    }

    override fun execute(world: World) {
        val gameObject: MovableGameObject = world.playersById[playerID] ?: return

        var newX = gameObject.x
        var newY = gameObject.y
        when (direction) {
            Direction.LEFT -> newX -= 1
            Direction.RIGHT -> newX += 1
            Direction.UP -> newY += 1
            Direction.DOWN -> newY -= 1
        }

        if (world.map.cellIsEmpty(newX, newY)) {
            for (other in world.getMovableGameObjectsOnMap(newX, newY)) {
                if (gameObject is Attacker) {
                    gameObject.attack(other)
                }
                if (other is Attacker) {
                    other.attack(gameObject)
                }
            }
            world.movePlayerTo(playerID, newX, newY)
        }
    }
}

class Shoot : GameAction() {

    override fun getName(): String {
        return "shoot"
    }

    override fun execute(world: World) {
        TODO("Not yet implemented")
    }
}

class Exit : GameAction() {
    override fun getName(): String {
        return "exit"
    }

    override fun execute(world: World) {
        TODO("Not yet implemented")
    }
}
