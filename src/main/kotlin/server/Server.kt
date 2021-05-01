package server

import java.net.ServerSocket
import utils.SocketWrapper

class Server(private val serverSocket: ServerSocket) {
    // Мапа id -> Player

    companion object {
        val listeners: MutableList<ServerListener> = mutableListOf()
    }

    private fun startImpl() {
        println("Server was started on localhost:${serverSocket.localPort}. Welcome!")
        // Создать World
        try {
            while (true) {
                // кто-то подрубается
                val accept = serverSocket.accept()

                // Регистрация --- нужна только серверу
                // Новый id

                val communication = SocketWrapper(accept)


                // Отдельный
                val listener = ServerListener(communication /* id игрока */)
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