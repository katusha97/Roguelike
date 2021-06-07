package server

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.int
import io.ktor.network.sockets.*
import kotlinx.coroutines.*
import server.engine.BotController
import server.engine.GameEngine
import server.notifier.UpdateWorldNotifier
import utils.ServerSocketWrapper

class Server(private val serverSocket: ServerSocket) {
    private fun startImpl() {
        runBlocking {
            println("Server was started on ${serverSocket.localAddress}. Welcome!")

            val register = PlayerRegister()

            val gameEngine = GameEngine()
            launch {
                gameEngine.start()
            }

            val clientNotifier = UpdateWorldNotifier(gameEngine)
            launch {
                clientNotifier.start()
            }

            repeat(8) {
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
                    val listener = ServerListener(communication, gameEngine, clientNotifier, targetUserID)
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
