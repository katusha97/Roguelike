package common.protocol

import common.model.WorldProposal
import common.protocol.commands.Action
import common.protocol.commands.ActionRequest
import common.protocol.commands.InitializeWorld
import common.protocol.commands.UpdateWorld
import utils.SocketWrapper

class ClientProtocol(communication: SocketWrapper): ProtocolBase(communication) {
    fun sendAction(action: Action) {
        send(ActionRequest(action))
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