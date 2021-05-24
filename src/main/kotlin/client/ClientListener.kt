package client

import client.view.View
import common.protocol.ClientProtocol
import utils.ClientSocketWrapper
import utils.toStringConsole

class ClientListener(communication: ClientSocketWrapper, private val frame: View): Thread() {
    private val protocol = ClientProtocol(communication)

    override fun run() {
        val idClass = protocol.readClientId()

        while (true) {
            val world = protocol.readUpdateWorld()
            frame.updateWorld(world, idClass.id)
        }
    }

    private fun log(msg: String) {
        println("ClientListener: $msg")
    }
}