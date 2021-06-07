package common.protocol

import common.model.Id
import common.model.World
import common.protocol.commands.*
import io.ktor.utils.io.*
import io.ktor.utils.io.jvm.javaio.*
import utils.ServerSocketWrapper
import java.io.OutputStream
import java.io.PrintStream

class ServerProtocol(communication: ServerSocketWrapper): ProtocolBase(communication) {
    fun sendServerCommand(cmd: ServerCommand) {
        send(cmd)
    }

    fun sendClientId(id: Id) {
        send(SendIdToClient(id))
    }

    suspend fun readAction(): ActionPlayer {
        val actionRequest = read<ActionRequest>()
        return actionRequest.action
    }
}