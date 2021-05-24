package common.protocol

import common.protocol.commands.Command
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import utils.ServerSocketWrapper
import java.lang.Exception

open class ClientServerCommunicationException(msg: String) : Exception(msg)

class ClientDisconnectException(msg: String) : ClientServerCommunicationException(msg)

abstract class ProtocolBase(val communication: ServerSocketWrapper) {
    protected val jsonConfig = Json {
        allowStructuredMapKeys = true
    }

    protected inline fun <reified T: Command> send(command: T) {
        communication.writeLine(jsonConfig.encodeToString(command))
    }

    protected suspend inline fun <reified T: Command> read() : T {
        val msg: String = try {
            communication.readLine()
        } catch (e: Exception) {
            throw ClientDisconnectException(
                "Socket readLine returns null (perhaps client disconnected or server close connection)"
            )
        }

        val cmd = jsonConfig.decodeFromString<T>(msg)
        return cmd
    }
}
