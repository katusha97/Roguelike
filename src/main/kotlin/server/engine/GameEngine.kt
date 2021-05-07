package server.engine

import common.model.ActiveAngryBot
import common.model.PassiveAngryBot
import common.model.World
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import server.notifier.UpdateWorldNotifier

class GameEngine {
    private val world: World = WorldGenerator(27, 27).generateWorld()
    private val channel = Channel<GameEngineRequest>()

    // Вызывается из других корутин
    suspend fun request(cmd: GameEngineRequest) {
        channel.send(cmd)
    }

    // Вызывается в отдельной корутине
    suspend fun start() {
        while (true) {
            val cmd = channel.receive()
            println("Game Engine receive request: $cmd")

            when (cmd) {
                is GetWorld ->
                    cmd.complete(world)
                is GameAction ->
                    cmd.execute(world)
            }
        }
    }
}