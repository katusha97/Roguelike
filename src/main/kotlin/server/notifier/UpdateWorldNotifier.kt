package server.notifier

import common.model.World
import kotlinx.coroutines.channels.Channel

// Publisher --- сообщает всем подписчикам об изменении мира.
class UpdateWorldNotifier {
    private val subscribers = mutableListOf<ISubscriber>()
    private val channel = Channel<World>()

    private suspend fun notifySubscribers(world: World) {
        for (s in subscribers) {
            s.update(world)
        }
    }

    // Вызывается из других корутин
    fun subscribe(s: ISubscriber) {
        subscribers.add(s)
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