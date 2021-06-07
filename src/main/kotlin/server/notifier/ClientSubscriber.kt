package server.notifier

import common.model.World
import common.protocol.ServerProtocol
import common.protocol.commands.UpdateWorld
import utils.ServerSocketWrapper

class ClientSubscriber(socket: ServerSocketWrapper) : ISubscriber {
    private val protocol = ServerProtocol(socket)

    override suspend fun update(world: World) {
        protocol.sendServerCommand(UpdateWorld(world))
    }
}