package server

import common.protocol.commands.*
import common.protocol.ClientServerCommunicationException
import common.protocol.ServerProtocol
import server.engine.WorldGenerator
import utils.SocketWrapper

class ServerListener(private val communication: SocketWrapper): Thread() {
    private val protocol = ServerProtocol(communication)

    private fun startCommunication() {
        log("Client connected with ${communication.host}:${communication.port}")

        val gen = WorldGenerator(21, 21)
        val world = gen.generateWorld()
        log("World map has generated")

        protocol.sendInitializeWorld(world)
        log("World has sent to client")

        while (true) {
            val action = protocol.readAction()

            when (action) {
                is ExitFromPlayer -> {
                    log("Stop working with client")
                    protocol.sendServerCommand(ExitAccept())
                    break
                } else -> {
                    //  Вот здесть уже не знаю как будет логика реализовываться, потому что в командах
                    // ...FromPlayer нет execute()
//                    action.execute(world)
                    protocol.sendServerCommand(UpdateWorld(world))
                }
            }
        }
    }

    override fun run() {
        try {
            startCommunication()
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
