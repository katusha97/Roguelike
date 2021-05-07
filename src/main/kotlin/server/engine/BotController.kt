package server.engine

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
            delay(500)
            val world = getWorld()

            val bot = world.players[id] ?: throw Exception("TODO")
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

            val direction = freeDirections.random()
            prevDirection = direction
            gameEngine.request(Move(id, direction))
        }
    }
}