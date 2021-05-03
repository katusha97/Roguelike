package server.engine

import common.model.World
import kotlinx.coroutines.channels.Channel
import server.ClientNotifier

class GameEngine(private val clientNotifier: ClientNotifier) {
    private val world: World = WorldGenerator(21, 21).generateWorld()
    private val channel = Channel<GameAction>()

    // Вызывается из других корутин
    suspend fun execute(cmd: GameAction) {
        channel.send(cmd)
    }

    // Вызывается в отдельной корутине
    suspend fun start() {
        while (true) {
            val cmd = channel.receive()
            println("Game Engine receive cmd: $cmd")

            cmd.execute(world)
            clientNotifier.updateWorld(world)
        }
    }
}