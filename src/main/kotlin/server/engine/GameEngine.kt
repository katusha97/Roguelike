package server.engine

import common.model.World
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import server.notifier.UpdateWorldNotifier

class GameEngine(private val clientNotifier: UpdateWorldNotifier) {
    private val worldMutex = Mutex()
    private val world: World = WorldGenerator(21, 21).generateWorld()

    private val channel = Channel<GameAction>()

    suspend fun getWorld(): World {
        worldMutex.withLock {
            return world
        }
    }

    // Вызывается из других корутин
    suspend fun execute(cmd: GameAction) {
        channel.send(cmd)
    }

    // Вызывается в отдельной корутине
    suspend fun start() {
        while (true) {
            val cmd = channel.receive()
            println("Game Engine receive cmd: $cmd")

            worldMutex.withLock {
                cmd.execute(world)
            }
            clientNotifier.updateWorld(world)
        }
    }
}