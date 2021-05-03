package client

import client.view.View
import common.protocol.ClientProtocol
import common.protocol.commands.ExitAccept
import common.protocol.commands.UpdateWorld
import utils.ClientSocketWrapper
import utils.toStringConsole
import kotlin.system.exitProcess

class BotListener(communication: ClientSocketWrapper): Thread() {
    private val protocol = ClientProtocol(communication)

    override fun run() {
        var world = protocol.readUpdateWorld()
        while (true) {
            when (val cmd = protocol.readServerCommand()) {
                is UpdateWorld -> {
                    world = cmd.world

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