package client.controller

import common.protocol.ClientProtocol
import common.protocol.commands.*
import utils.SocketWrapper

class ControllerImpl(communication: SocketWrapper) : Controller {
    private val protocol = ClientProtocol(communication)

    override fun move(direction: Direction) {
        protocol.sendAction(MoveFromPlayer(direction))
    }

    override fun shoot() {
        protocol.sendAction(ShootFromPlayer())
    }

    override fun exit() {
        protocol.sendAction(ExitFromPlayer())
    }

    override fun pause() {
        TODO("Not yet implemented")
    }

    override fun save() {
        TODO("Not yet implemented")
    }
}