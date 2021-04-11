package client.controller

import common.commands.Command
import common.commands.Exit
import common.commands.Move
import common.commands.Shoot
import common.model.World
import kotlinx.serialization.*
import kotlinx.serialization.json.Json
import utils.SocketWrapper

class ControllerImpl(override var world: World, private val socket: SocketWrapper) : Controller {

    private fun send(command: Command) {
        socket.writeLine(Json.encodeToString(command))
    }

    override fun move(direction: Move.Direction) {
        send(Move(direction))
    }

    override fun shoot() {
        send(Shoot())
    }

    override fun exit() {
        send(Exit())
    }

    override fun pause() {
        TODO("Not yet implemented")
    }

    override fun save() {
        TODO("Not yet implemented")
    }
}