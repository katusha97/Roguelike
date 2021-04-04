package server

import utils.Communication

class Listener(val communication: Communication): Thread() {
    override fun run() {
        println("Client connected with ${communication.host}:${communication.port}")

        val request = communication.readLine()
        println("Receive msg:${request}")
        val response = "Your input: $request"
        communication.writeLine(response)
    }
}
