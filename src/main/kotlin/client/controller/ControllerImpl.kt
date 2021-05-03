package client.controller

import common.protocol.ClientProtocol
import common.protocol.commands.Direction
import common.protocol.commands.ExitFromPlayer
import common.protocol.commands.MoveFromPlayer
import common.protocol.commands.ShootFromPlayer
import utils.ClientSocketWrapper

class ControllerImpl(communication: ClientSocketWrapper) : Controller {
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