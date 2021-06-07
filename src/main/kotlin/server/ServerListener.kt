package server

import common.model.Id
import common.protocol.commands.*
import common.protocol.ClientServerCommunicationException
import common.protocol.ServerProtocol
import server.engine.CreatePlayer
import server.engine.DeleteMovableObject
import server.engine.GameEngine
import server.engine.Move
import server.notifier.ClientSubscriber
import server.notifier.UpdateWorldNotifier
import utils.ServerSocketWrapper

class ServerListener(
    private val communication: ServerSocketWrapper,
    private val gameEngine: GameEngine,
    private val clientNotifier: UpdateWorldNotifier,
    private val id: Int) {

    private val protocol = ServerProtocol(communication)
    private val subscriber = ClientSubscriber(communication)

    private suspend fun startCommunicationImpl() {
        log("Client connected with ${communication.host}:${communication.port}")
        gameEngine.request(CreatePlayer(id))

        protocol.sendClientId(Id(id))
        clientNotifier.subscribe(subscriber)

        while (true) {
            when (val action = protocol.readAction()) {
                is ExitFromPlayer -> {
                    cleanUp()
                    protocol.sendServerCommand(ExitAccept())
                }
                is MoveFromPlayer -> {
                    gameEngine.request(Move(id, action.direction))
                }
                else -> {
                    log("Unsupported action: $action")
                }
            }
        }
    }

    private suspend fun cleanUp() {
        clientNotifier.unsubscribe(subscriber)
        gameEngine.request(DeleteMovableObject(id))
    }

    suspend fun startCommunication() {
        try {
            startCommunicationImpl()
        } catch (e: ClientServerCommunicationException) {
            log("Communication ERROR: $e")
        } catch (e: Exception) {
            log("Critical ERROR: $e")
        } finally {
            cleanUp()
        }
    }

    private fun log(msg: String) {
        println("ServerListener $id: $msg")
    }

    private fun logErr(msg: String) {
        System.err.println("ServerListener $id ERROR: $msg")
    }
}
