package common.protocol

import common.model.WorldProposal
import common.protocol.commands.*
import utils.SocketWrapper

class ServerProtocol(communication: SocketWrapper): ProtocolBase(communication) {
    fun sendServerCommand(cmd: ServerCommand) {
        send(cmd)
    }

    fun sendUpdateWorld(world: WorldProposal) {
        send(UpdateWorld(world))
    }

    fun sendInitializeWorld(world: WorldProposal) {
        send(InitializeWorld(world))
    }

    fun sendExitAccept() {
        send(ExitAccept())
    }

    fun readAction(): Action {
        val actionRequest = read<ActionRequest>()
        return actionRequest.action
    }
}