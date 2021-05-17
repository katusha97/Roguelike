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
        world.playersById[id] = player
        if (!world.playersOnMap.containsKey(Pair(posX, posY))) {
            world.playersOnMap[Pair(posX, posY)] = HashSet()
        }
        world.playersOnMap[Pair(posX, posY)]!!.add(player)
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
        val newBot: MovableGameObject = if (r <= 40) {
            PassiveAngryBot(x, y, 100, 10, id)
        } else {
            ActiveAngryBot(x, y, 100, 10, id)
        }
        world.playersById[id] = newBot
        if (!world.playersOnMap.containsKey(Pair(x, y))) {
            world.playersOnMap[Pair(x, y)] = HashSet()
        }
        world.playersOnMap[Pair(x, y)]!!.add(newBot)
    }
}


class Move(private val playerID: Int, private val direction: Direction) : GameAction() {
    override fun getName(): String {
        return "move"
    }

    override fun execute(world: World) {
        val gameObject: MovableGameObject = world.playersById[playerID] ?: return
        var x = gameObject.x
        var y = gameObject.y
        val prevX = x
        val prevY = y
        when (direction) {
            Direction.LEFT -> x -= 1
            Direction.RIGHT -> x += 1
            Direction.UP -> y += 1
            Direction.DOWN -> y -= 1
        }

        // TODO: Improve game object to stop this trash
        if (!world.map.stones.any { it.x == x && it.y == y }) {
            world.playersOnMap[Pair(prevX, prevY)]!!.remove(gameObject)
            gameObject.move(x, y)
            if (gameObject is Player) {
                if (world.playersOnMap.containsKey(Pair(x, y))) {
                    val playerOnThisRect = world.playersOnMap[Pair(x, y)]
                    if (playerOnThisRect!!.isNotEmpty()) {
                        gameObject.health -= 5
                    }
                }
            }
            if (!world.playersOnMap.containsKey(Pair(x, y))) {
                world.playersOnMap[Pair(x, y)] = HashSet()
            }
            world.playersOnMap[Pair(x, y)]!!.add(gameObject)
        }
    }
}

//fun fromCoords(x: Int, y: Int, world: World): Int {
//    return (y - 1) * world.map.sizeX + x
//}

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
