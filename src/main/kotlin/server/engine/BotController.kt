package server.engine

import common.model.StaticGameObject
import common.model.StoneItemProposal
import common.model.World
import common.protocol.commands.Direction
import kotlinx.coroutines.delay
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import server.notifier.ISubscriber

class BotController(private val id: Int, private val gameEngine: GameEngine): ISubscriber {
    private lateinit var currentWorld: World
    private val mutex = Mutex()

    override suspend fun update(world: World) {
        mutex.withLock {
            currentWorld = world
        }
    }


    private suspend fun getWorld(): World {
        mutex.withLock {
            return currentWorld
        }
    }

    @OptIn(ExperimentalStdlibApi::class)
    suspend fun start() {
        currentWorld = gameEngine.getWorld()

        gameEngine.execute(CreatePlayer(id))

        var prevDirection: Direction = Direction.LEFT
        while (true) {
            delay(1000)
            val world = getWorld()

            val bot = world.players[id] ?: throw Exception("TODO")
            val freeDirections =
                getNeighboursCellsWithDirection(bot.x, bot.y)
                    .filter {
                        val x = it.first.x
                        val y = it.first.y
                        // TODO: 1) Make stones hash table (x, y) -> Cell
                        !world.map.stones.any { it.x == x && it.y == y }
                        // TODO: 2) Make check, that other player (bot) not in cell
                    }
                    .map { it.second }
                    .toMutableList()
            if (freeDirections.size > 1) {
                freeDirections.remove(revertedDirection(prevDirection))
            }

            val direction = freeDirections.random()
            prevDirection = direction
            gameEngine.execute(Move(id, direction))
        }
    }
}