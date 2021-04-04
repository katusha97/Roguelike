package server

import java.net.ServerSocket
import kotlinx.coroutines.*
import utils.Communication
import java.lang.Runnable

class Server(private val serverSocket: ServerSocket) {
    companion object {
        val listeners: MutableList<Listener> = mutableListOf()
    }

    private fun startImpl() {
        println("Server was started on localhost:${serverSocket.localPort}. Welcome!")
        while (true) {
            try {
                val accept = serverSocket.accept()
                val communication = Communication(accept)

                val listener = Listener(communication)
                listeners.add(listener)
                listener.start()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun start() = serverSocket.use { startImpl() }
}

fun main() {
    val server = Server(ServerSocket(8000))
    server.start()
}