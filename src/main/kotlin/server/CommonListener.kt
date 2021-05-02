package server

import kotlinx.coroutines.channels.Channel

class CommonListener {
    private val channel = Channel<Int>()

    suspend fun send(x: Int) {
        channel.send(x)
    }

    suspend fun execute() {
        while (true) {
            val x = channel.receive()
            println("Common Listener: $x")
        }
    }
}