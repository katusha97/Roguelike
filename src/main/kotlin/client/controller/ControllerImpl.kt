package client.controller

import common.protocol.commands.Exit
import common.protocol.commands.Move
import common.protocol.commands.Shoot
import common.protocol.ClientProtocol
import utils.SocketWrapper

class ControllerImpl(communication: SocketWrapper) : Controller {
    private val protocol = ClientProtocol(communication)

    override fun move(direction: Move.Direction) {
        protocol.sendAction(Move(direction))
    }

    override fun shoot() {
        protocol.sendAction(Shoot())
    }

    override fun exit() {
        protocol.sendAction(Exit())
    }

    override fun pause() {
        TODO("Not yet implemented")
    }

    override fun save() {
        TODO("Not yet implemented")
    }
}