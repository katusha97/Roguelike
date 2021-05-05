package server

import io.ktor.network.selector.*
import io.ktor.network.sockets.*
import kotlinx.coroutines.*
import server.engine.BotController
import server.engine.GameEngine
import server.notifier.ClientSubscriber
import server.notifier.UpdateWorldNotifier
import utils.ServerSocketWrapper
import java.net.InetSocketAddress

class Server(private val serverSocket: ServerSocket) {
    private fun startImpl() {
        runBlocking {
            println("Server was started on localhost:${serverSocket.localAddress}. Welcome!")

            val register = PlayerRegister()

            val clientNotifier = UpdateWorldNotifier()
            launch {
                clientNotifier.start()
            }

            val gameEngine = GameEngine(clientNotifier)
            launch {
                gameEngine.start()
            }

            // Создаём ботов
            repeat(5) {
                val newBotId = register.getNewId()
                val bot = BotController(newBotId, gameEngine)
                launch {
                    bot.start()
                }
            }

            try {
                while (true) {
                    println("Waiting accept...")
                    val accept = serverSocket.accept()
                    val targetUserID = register.getNewId()
                    println("Accept: user with id $targetUserID connected")

                    val communication = ServerSocketWrapper(accept)
                    clientNotifier.subscribe(ClientSubscriber(communication))

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
