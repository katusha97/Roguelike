package server

import io.ktor.network.selector.*
import io.ktor.network.sockets.*
import io.ktor.utils.io.jvm.javaio.*
import kotlinx.coroutines.*
import utils.ServerSocketWrapper
import java.net.InetSocketAddress

class Server(private val serverSocket: ServerSocket) {
    companion object {
        val listeners: MutableList<ServerListener> = mutableListOf()
    }

    private fun startImpl() {
        runBlocking {
            println("Server was started on localhost:${serverSocket.localAddress}. Welcome!")
            try {
                while (true) {
                    println("Waiting accept...")
                    val accept = serverSocket.accept()
                    println("Waiting accepted!")

                    val communication = ServerSocketWrapper(accept)
                    val listener = ServerListener(communication)
                    listeners.add(listener)

                    launch {
                        println("Inside coroutine")
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
