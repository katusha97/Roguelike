package common.protocol

import common.model.WorldProposal
import common.protocol.commands.*
import utils.SocketWrapper

class ClientProtocol(communication: SocketWrapper): ProtocolBase(communication) {
    fun sendAction(action: Action) {
        send(ActionRequest(action))
    }

    fun readServerCommand(): ServerCommand {
        val cmd = read<ServerCommand>()
        return cmd
    }

    fun readInitializeWorld(): WorldProposal {
        val updateWorldRequest = read<InitializeWorld>()
        return updateWorldRequest.world
    }

    fun readUpdateWorld(): WorldProposal {
        val updateWorldRequest = read<UpdateWorld>()
        return updateWorldRequest.world
    }
}