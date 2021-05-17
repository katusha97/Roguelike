package common.protocol

import common.model.Id
import common.model.World
import common.protocol.commands.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import utils.ClientSocketWrapper
import java.lang.Exception

class ClientProtocol(private val communication: ClientSocketWrapper) {
    private inline fun <reified T: Command> send(command: T) {
        communication.writeLine(Json { allowStructuredMapKeys = true }.encodeToString(command))
    }

    private inline fun <reified T: Command> read() : T {
        val msg: String = try {
            println("here")
            communication.readLine()
        } catch (e: Exception) {
            throw ClientDisconnectException(
                "Socket readLine returns null (perhaps client disconnected or server close connection)"
            )
        }

        val cmd = Json { allowStructuredMapKeys = true }.decodeFromString<T>(msg)
        return cmd
    }

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

    fun readClientId(): Id {
        val readClientId = read<SendIdToClient>()
        return readClientId.id
    }
}