package client.controller

import client.view.View
import common.protocol.commands.Exit
import common.protocol.commands.Move
import common.protocol.commands.Shoot
import common.model.WorldProposal
import common.protocol.ClientProtocol
import utils.SocketWrapper

class ControllerImpl(private var worldVar: WorldProposal, communication: SocketWrapper) : Controller {
    private lateinit var view: View
    private val protocol = ClientProtocol(communication)

    override var world: WorldProposal
        get() = worldVar
        set(value) {
            worldVar = value
            view.repaint()
        }

    fun setView(view: View) {
        this.view = view
    }

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