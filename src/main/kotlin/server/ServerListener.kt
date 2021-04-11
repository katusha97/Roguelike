package server

import common.commands.Command
import common.commands.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import utils.SocketWrapper

class ServerListener(val communication: SocketWrapper): Thread() {
    override fun run() {
        println("ServerListener: client connected with ${communication.host}:${communication.port}")

        while (true) {
            val request: String? = try { communication.readLine() } catch (e: Exception) { null }
            if (request == null) {
                log("client disconnected")
                break
            }

            val decoded = Json.decodeFromString<Command>(request)
            log("Receive msg:$decoded")

            when (decoded) {
                is Exit -> {
                    log("stop working with client")
                    val response = Json.encodeToString<Command>(decoded)
                    communication.writeLine(response)
                    break
                }
                else -> {
                    val response = Json.encodeToString(decoded)
                    communication.writeLine(response)
                }
            }
        }
    }

    private fun log(msg: String) {
        println("ServerListener: $msg")
    }
}
