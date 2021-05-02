package server

import common.protocol.commands.*
import common.protocol.ClientServerCommunicationException
import common.protocol.ServerProtocol
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import server.engine.WorldGenerator
import utils.ServerSocketWrapper

class ServerListener(private val communication: ServerSocketWrapper) {
    private val protocol = ServerProtocol(communication)

    private suspend fun startCommunicationImpl() {
        log("Client connected with ${communication.host}:${communication.port}")

        val gen = WorldGenerator(21, 21)
        val world = gen.generate()
        log("World map has generated")

        protocol.sendInitializeWorld(world)

        while (true) {
            log("Read action...")
            val action = protocol.readAction()

            when (action) {
                is Exit -> {
                    log("Stop working with client")
                    protocol.sendServerCommand(ExitAccept())
                    break
                } else -> {
                    action.execute(world)
                    protocol.sendServerCommand(UpdateWorld(world))
                }
            }
        }
    }

    suspend fun startCommunication() {
        try {
            startCommunicationImpl()
        } catch (e: ClientServerCommunicationException) {
            log("Communication ERROR: $e")
        } catch (e: Exception) {
            log("Critical ERROR: $e")
        }
    }

    private fun log(msg: String) {
        println("ServerListener: $msg")
    }

    private fun logErr(msg: String) {
        System.err.println("ServerListener ERROR: $msg")
    }
}
