package server.notifier

import common.model.World
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import server.engine.GameEngine
import server.engine.GetWorld

// Publisher --- сообщает всем подписчикам об изменении мира.
class UpdateWorldNotifier(private val gameEngine: GameEngine) {
    private val subscribers = mutableListOf<ISubscriber>()

    private suspend fun notifySubscribers(world: World) {
        for (s in subscribers) {
            s.update(world)
        }
    }

    // Вызывается из других корутин
    fun subscribe(s: ISubscriber) {
        subscribers.add(s)
    }

    // Вызывается в отдельной корутине
    suspend fun start() {
        while (true) {
            delay(50)

            val request = GetWorld()
            gameEngine.request(request)
            val world = request.await()

            notifySubscribers(world)
        }
    }
}