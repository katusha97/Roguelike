package server.notifier

import common.model.World
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import server.engine.GameEngine
import server.engine.GetWorld

// Publisher --- сообщает всем подписчикам об изменении мира.
class UpdateWorldNotifier(private val gameEngine: GameEngine) {
    private val subscribers = mutableListOf<ISubscriber>()
    private val mutex = Mutex()

    private suspend fun notifySubscribers(world: World) {
        for (s in subscribers) {
            s.update(world)
        }
    }

    // Вызывается из других корутин
    suspend fun subscribe(s: ISubscriber) {
        mutex.withLock {
            subscribers.add(s)
        }
    }

    suspend fun unsubscribe(s: ISubscriber) {
        mutex.withLock {
            subscribers.remove(s)
        }
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