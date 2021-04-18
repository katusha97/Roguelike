package client

import client.controller.ControllerImpl
import client.view.View
import common.model.*
import utils.SocketWrapper
import java.net.Socket

fun main() {
    val socket = SocketWrapper(Socket("127.0.0.1", 8000))
    val controller = ControllerImpl(
        WorldProposal(
            LevelStaticMapProposal(HashSet(), 0, 0),
            PlayerProposal(0, 0, 0)
        ), socket
    )
    val frame = View("Roguelike", controller)
    controller.setView(frame)
    frame.isVisible = true

    val listener = ClientListener(socket, controller)
    listener.run()
}