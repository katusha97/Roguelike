package client

import utils.Creater

fun main() {
    val socket = Creater("127.0.0.1", 8000)
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

