package client

import client.view.View
import common.commands.Command
import common.commands.Exit
import common.commands.Move
import common.commands.Shoot
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import utils.SocketWrapper
import kotlin.system.exitProcess

class ClientListener(val communication: SocketWrapper, val view: View): Thread() {
    override fun run() {
        while (true) {
            val msg = communication.readLine()
            val command = Json.decodeFromString<Command>(msg)
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