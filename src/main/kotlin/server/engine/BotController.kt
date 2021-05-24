package server.engine

import common.model.*
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
            delay(500)
            val world = getWorld()

            val bot = (world.playersById[id] as? Bot) ?: throw Exception("TODO")



            val directionToPlayers = getDirectionToPlayer(bot.x, bot.y, world)
            val currentFreeDirections: List<Direction>

            when (bot) {
                is ActiveAngryBot ->
                    if (directionToPlayers != null) {
                        currentFreeDirections = listOf(directionToPlayers)
                    } else {
                        currentFreeDirections = getFreeDirections(bot.x, bot.y, world)
                        if (currentFreeDirections.size > 1) {
                            currentFreeDirections.remove(revertedDirection(prevDirection))
                        }
                    }
                is PassiveAngryBot -> {
                    currentFreeDirections = getFreeDirections(bot.x, bot.y, world)

                    if (directionToPlayers != null) {
                        currentFreeDirections.remove(directionToPlayers)
                    }
                    if (currentFreeDirections.size > 1) {
                        currentFreeDirections.remove(revertedDirection(prevDirection))
                    }
                }
            }
            if (currentFreeDirections.isNotEmpty()) {
                val direction = currentFreeDirections.random()
                prevDirection = direction
                gameEngine.request(Move(id, direction))
            }
        }
    }

    private fun getFreeDirections(x: Int, y: Int, world: World): MutableList<Direction> {
        return getNeighboursCellsWithDirection(x, y)
            .filter {
                world.map.cellIsEmpty(it.first.x, it.first.y)
            }
            .map { it.second }
            .toMutableList()
    }

    private fun getDirectionToPlayer(x: Int, y: Int, world: World): Direction? {
        val existPlayerInCells = fun(cells: List<Pair<Int, Int>>) =
            cells
                .flatMap { world.getMovableGameObjectsOnMap(it.first, it.second) }
                .filterIsInstance<Player>()
                .isNotEmpty()

        val constructPathAlongDirection = fun(count: Int, dir: Direction): MutableList<Pair<Int, Int>> {
            var currX = x
            var currY = y

            val xs = mutableListOf<Pair<Int, Int>>()
            repeat(count) {
                when (dir) {
                    Direction.RIGHT -> currX += 1
                    Direction.LEFT -> currX -= 1
                    Direction.UP -> currY += 1
                    Direction.DOWN -> currY -= 1
                }
                if (!world.map.cellIsEmpty(currX, currY)) {
                    return xs
                }
                xs.add(Pair(currX, currY))
            }
            return xs
        }

        for (dir in Direction.values()) {
            if (existPlayerInCells(constructPathAlongDirection(5, dir))) {
                return dir
            }
        }
        return null
    }
}
