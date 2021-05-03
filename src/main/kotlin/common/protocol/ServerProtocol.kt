package common.protocol

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

    fun sendUpdateWorld(world: World) {
        send(UpdateWorld(world))
    }

    fun sendInitializeWorld(world: World) {
        send(InitializeWorld(world))
    }

    fun sendExitAccept() {
        send(ExitAccept())
    }

    suspend fun readAction(): ActionPlayer {
        val actionRequest = read<ActionRequest>()
        return actionRequest.action
    }
}