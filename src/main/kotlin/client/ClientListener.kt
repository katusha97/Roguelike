package client

import client.view.View
import common.protocol.ClientProtocol
import utils.ClientSocketWrapper
import utils.toStringConsole

class ClientListener(communication: ClientSocketWrapper, private val frame: View): Thread() {
    private val protocol = ClientProtocol(communication)

    override fun run() {
        val idClass = protocol.readClientId()
        var world = protocol.readUpdateWorld()
        frame.updateWorld(world, idClass.id)

        log("Retrieve world:")
        print(world.toStringConsole())

        while (true) {
            world = protocol.readUpdateWorld()
            frame.updateWorld(world, idClass.id)

            log("Retrieved updated world:")
            println(world.toStringConsole())
        }
    }

    private fun log(msg: String) {
        println("ClientListener: $msg")
    }
}