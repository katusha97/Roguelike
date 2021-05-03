package client

import client.view.View
import common.protocol.ClientProtocol
import utils.ClientSocketWrapper
import utils.toStringConsole

class ClientListener(communication: ClientSocketWrapper, private val frame: View): Thread() {
    private val protocol = ClientProtocol(communication)

    override fun run() {
        var world = protocol.readUpdateWorld()
        frame.updateWorld(world)

        log("Retrieve world:")
        print(world.toStringConsole())

        while (true) {
            world = protocol.readUpdateWorld()
            frame.updateWorld(world)

            log("Retrieved updated world:")
            println(world.toStringConsole())
        }
    }

    private fun log(msg: String) {
        println("ClientListener: $msg")
    }
}