package client

import utils.SocketWrapper

fun main() {
    val socket = SocketWrapper("127.0.0.1", 8000)
    try {
        socket.use {
            Thread {
                println("Connected to server!")
                val request = "SPB"
                socket.writeLine(request)
                val response = socket.readLine()
                println("Response: $response")
            }.run()
        }
    }catch (e: Exception) {
        e.printStackTrace()
    }
}

