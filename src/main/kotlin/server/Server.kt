package server

import io.ktor.network.selector.*
import io.ktor.network.sockets.*
import kotlinx.coroutines.*
import server.engine.GameEngine
import utils.ServerSocketWrapper
import java.net.InetSocketAddress

class Server(private val serverSocket: ServerSocket) {
    private fun startImpl() {
        runBlocking {
            println("Server was started on localhost:${serverSocket.localAddress}. Welcome!")

            val clientNotifier = ClientNotifier()
            launch {
                clientNotifier.start()
            }

            val gameEngine = GameEngine(clientNotifier)
            launch {
                gameEngine.start()
            }

            var targetUserID = 0
            try {
                while (true) {
                    println("Waiting accept...")
                    val accept = serverSocket.accept()
                    targetUserID += 1
                    println("Accept: user with id $targetUserID connected")

                    val communication = ServerSocketWrapper(accept)
                    clientNotifier.subscribe(communication)

                    val listener = ServerListener(communication, gameEngine, targetUserID)
                    launch {
                        listener.startCommunication()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun start() = serverSocket.use { startImpl() }
}

fun main() {
    val serverSocket = aSocket(
        ActorSelectorManager(Dispatchers.IO))
        .tcp()
        .bind(InetSocketAddress("127.0.0.1", 8000)
    )
    val server = Server(serverSocket)
    server.start()
}
