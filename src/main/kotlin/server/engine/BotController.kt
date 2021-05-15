package server.engine

import common.model.Player
import common.model.World
import common.protocol.commands.Direction
import kotlinx.coroutines.delay

class BotController(private val id: Int, private val gameEngine: GameEngine) {
    private suspend fun getWorld(): World {
        val worldRequest = GetWorld()
        gameEngine.request(worldRequest)
        return worldRequest.await()
    }

    suspend fun start() {
        gameEngine.request(CreateBot(id))
        var prevDirection: Direction = Direction.LEFT

        while (true) {
            delay(1000)
            val world = getWorld()

            val bot = world.playersById[id] ?: throw Exception("TODO")
            var direction: Direction
            val currX = bot.x
            val currY = bot.y
//            if (getDirectionToPlayer(currX, currY, world) != null) {
//                direction = getDirectionToPlayer(currX, currY, world)!!
//            } else {
                val freeDirections =
                    getNeighboursCellsWithDirection(bot.x, bot.y)
                        .filter {
                            val x = it.first.x
                            val y = it.first.y
                            // TODO: 1) Make stones hash table (x, y) -> Cell
                            !world.map.stones.any { it.x == x && it.y == y }
                        }
                        .map { it.second }
                        .toMutableList()
                if (freeDirections.size > 1) {
                    freeDirections.remove(revertedDirection(prevDirection))
                }

                direction = freeDirections.random()
//            }
            prevDirection = direction
            gameEngine.request(Move(id, direction))
        }
    }

    private fun getDirectionToPlayer(currX: Int, currY: Int, world: World): Direction? {
        var countOfStep = 5
        var x = currX
        var y = currY
        while (x > 0 && !world.map.stones.any { it.x == x && it.y == y } && countOfStep > 0) {
            if (world.playersOnMap.containsKey(fromCoords(x, y, world))) {
                if (world.playersOnMap[fromCoords(x, y, world)]!!.any { it is Player }) {
                    return Direction.LEFT
                }
                countOfStep--
                x--
            }
        }
        x = currX
        countOfStep = 5
        while (x < world.map.sizeY - 1 && !world.map.stones.any { it.x == x && it.y == y } && countOfStep > 0) {
            if (world.playersOnMap.containsKey(fromCoords(x, y, world))) {
                if (world.playersOnMap[fromCoords(x, y, world)]!!.any { it is Player }) {
                    return Direction.RIGHT
                }
                countOfStep--
                x++
            }
        }
        x = currX
        countOfStep = 5
        while (y > 0 && !world.map.stones.any { it.x == x && it.y == y } && countOfStep > 0) {
            if (world.playersOnMap.containsKey(fromCoords(x, y, world))) {
                if (world.playersOnMap[fromCoords(x, y, world)]!!.any { it is Player }) {
                    return Direction.UP
                }
                countOfStep--
                y--
            }
        }
        y = currY
        countOfStep = 5
        while (y < world.map.sizeY - 1 && !world.map.stones.any { it.x == x && it.y == y } && countOfStep > 0) {
            if (world.playersOnMap.containsKey(fromCoords(x, y, world))) {
                if (world.playersOnMap[fromCoords(x, y, world)]!!.any { it is Player }) {
                    return Direction.DOWN
                }
                countOfStep--
                y++
            }
        }
        return null
    }
}

//fun toCoords(num: Int, world: World): Pair<Int, Int> {
//    return Pair(num % world.map.sizeX % num, num / world.map.sizeY)
//}