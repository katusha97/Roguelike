package server

import java.net.ServerSocket
import utils.SocketWrapper

fun main() {
    val server = ServerSocket(8000)

    server.use {
        println("Server was started!")
        while (true) {
            try {
                SocketWrapper(server).use {
                    Thread{
                        println("Client connected")
                        val request = it.readLine()
                        val response = "Your input: $request"
                        it.writeLine(response)
                    }.run()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}