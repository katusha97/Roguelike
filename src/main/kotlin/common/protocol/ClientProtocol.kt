package common.protocol

import common.model.World
import common.protocol.commands.*
import utils.SocketWrapper

class ClientProtocol(communication: SocketWrapper): ProtocolBase(communication) {
    fun sendAction(action: ActionPlayer) {
        send(ActionRequest(action))
    }

    fun readServerCommand(): ServerCommand {
        val cmd = read<ServerCommand>()
        return cmd
    }

    fun readInitializeWorld(): World {
        val updateWorldRequest = read<InitializeWorld>()
        return updateWorldRequest.world
    }

    fun readUpdateWorld(): World {
        val updateWorldRequest = read<UpdateWorld>()
        return updateWorldRequest.world
    }
}