package client

import client.view.View
import common.protocol.ClientProtocol
import common.protocol.commands.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import utils.SocketWrapper
import utils.toStringConsole
import kotlin.system.exitProcess

class ClientListener(communication: SocketWrapper, private val view: View): Thread() {
    private val protocol = ClientProtocol(communication)

    override fun run() {
//        var world = protocol.readInitializeWorld()
//
//        log("Retrieve world:")
//        print(world.toStringConsole())

        while (true) {
            val cmd = protocol.readServerCommand()

            when (cmd) {
                is ExitAccept -> {
                    log("Exit accepted. By-by!")
                    exitProcess(0)
                }
                is InitializeWorld -> {
                    view.controller.world = cmd.world
                }
                is UpdateWorld -> {
                    view.controller.world = cmd.world
                    log("Retrieved updated world:")
//                    println(world.toStringConsole())
                }
            }
        }
    }

    private fun log(msg: String) {
        println("ClientListener: $msg")
    }
}