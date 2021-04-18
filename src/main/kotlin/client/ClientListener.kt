package client

import client.view.View
import common.protocol.ClientProtocol
import common.protocol.commands.*
import utils.SocketWrapper
import utils.toStringConsole
import kotlin.system.exitProcess

class ClientListener(communication: SocketWrapper, private val frame: View): Thread() {
    private val protocol = ClientProtocol(communication)

    override fun run() {
        var world = protocol.readInitializeWorld()
        frame.updateWorld(world)

        log("Retrieve world:")
        print(world.toStringConsole())

        while (true) {
            when (val cmd = protocol.readServerCommand()) {
                is ExitAccept -> {
                    log("Exit accepted. By-by!")
                    exitProcess(0)
                }
                is UpdateWorld -> {
                    world = cmd.world
                    frame.updateWorld(world)

                    log("Retrieved updated world:")
                    println(world.toStringConsole())
                }
            }
        }
    }

    private fun log(msg: String) {
        println("ClientListener: $msg")
    }
}