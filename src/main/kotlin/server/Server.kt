package server

import java.net.ServerSocket
import utils.SocketWrapper

class Server(private val serverSocket: ServerSocket) {
    companion object {
        val listeners: MutableList<ServerListener> = mutableListOf()
    }

    private fun startImpl() {
        println("Server was started on localhost:${serverSocket.localPort}. Welcome!")
        try {
            while (true) {
                val accept = serverSocket.accept()
                val communication = SocketWrapper(accept)

                val listener = ServerListener(communication)
                listeners.add(listener)
                listener.start()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun start() = serverSocket.use { startImpl() }
}

fun main() {
    val server = Server(ServerSocket(8000))
    server.start()
}