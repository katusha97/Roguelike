package server

import common.protocol.commands.*
import common.protocol.ClientServerCommunicationException
import common.protocol.ServerProtocol
import server.engine.CreatePlayer
import server.engine.GameEngine
import server.engine.Move
import utils.ServerSocketWrapper

class ServerListener(private val communication: ServerSocketWrapper, private val gameEngine: GameEngine, private val id: Int) {
    private val protocol = ServerProtocol(communication)
    private var counter: Int = 0

    private suspend fun startCommunicationImpl() {
        log("Client connected with ${communication.host}:${communication.port}")
        gameEngine.request(CreatePlayer(id))

        while (true) {
            log("Read action...")
            val action = protocol.readAction()

            when (action) {
                is MoveFromPlayer -> {
                    gameEngine.request(Move(id, action.direction))
                }
                else -> {
                    log("Unsupported action: $action")
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
        println("ServerListener $id: $msg")
    }

    private fun logErr(msg: String) {
        System.err.println("ServerListener $id ERROR: $msg")
    }
}
