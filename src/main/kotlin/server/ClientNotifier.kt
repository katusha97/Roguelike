package server

import common.model.World
import common.protocol.ServerProtocol
import common.protocol.commands.UpdateWorld
import kotlinx.coroutines.channels.Channel
import utils.ServerSocketWrapper

class ClientNotifier {
    private val subscribers = mutableListOf<ServerProtocol>()
    private val channel = Channel<World>()

    private fun notifySubscribers(world: World) {
        for (s in subscribers) {
            s.sendUpdateWorld(world)
        }
    }

    // Вызывается из других корутин
    fun subscribe(socket: ServerSocketWrapper) {
        subscribers.add(ServerProtocol(socket))
    }

    // Вызывается из других корутин (из GameEngine)
    suspend fun updateWorld(world: World) {
        channel.send(world)
    }

    // Вызывается в отдельной корутине
    suspend fun start() {
        while (true) {
            val world = channel.receive()
            notifySubscribers(world)
        }
    }
}