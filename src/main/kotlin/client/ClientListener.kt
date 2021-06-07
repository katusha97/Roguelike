package client

import client.view.View
import common.protocol.ClientProtocol
import common.protocol.commands.ExitAccept
import common.protocol.commands.UpdateWorld
import utils.ClientSocketWrapper
import utils.toStringConsole

class ClientListener(communication: ClientSocketWrapper, private val frame: View) {
    private val protocol = ClientProtocol(communication)

    fun run() {
        val idClass = protocol.readClientId()

        while (true) {
            val cmd = protocol.readServerCommand()
            when (cmd) {
                is UpdateWorld -> {
                    frame.updateWorld(cmd.world, idClass.id)
                }
                is ExitAccept -> {
                    log("Exit accepted.")
                    break
                }
            }
        }
    }

    private fun log(msg: String) {
        println("ClientListener: $msg")
    }
}