package client

import client.view.View
import common.protocol.commands.Action
import common.protocol.commands.Exit
import common.protocol.commands.Move
import common.protocol.commands.Shoot
import common.protocol.ClientProtocol
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import utils.SocketWrapper
import utils.toStringConsole
import kotlin.system.exitProcess

class ClientListener(val communication: SocketWrapper, val view: View): Thread() {
    private val protocol = ClientProtocol(communication)

    override fun run() {
        val world = protocol.readInitializeWorld()

        log("Retrieve world:")
        print(world.toStringConsole())

        while (true) {
            val msg = communication.readLine()
            val command = Json.decodeFromString<Action>(msg)
            when (command) {
                is Exit -> exitProcess(0)
                is Move -> {
                    log("Move accepted")
                }
                is Shoot -> {
                    log("Shot accepted")
                }
            }
        }
    }

    private fun log(msg: String) {
        println("ClientListener: $msg")
    }
}