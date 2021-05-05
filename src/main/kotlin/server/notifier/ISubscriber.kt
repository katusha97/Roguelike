package server.notifier

import common.model.World

interface ISubscriber {
    suspend fun update(world: World)
}
