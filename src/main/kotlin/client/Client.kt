package client

import client.controller.ControllerImpl
import client.view.View
import common.model.*
import server.engine.WorldGenerator
import utils.SocketWrapper
import java.net.Socket

fun main() {
    val player = PlayerProposal(3, 4, 0)
    val socket = SocketWrapper(Socket("127.0.0.1", 8000))
    val controller = ControllerImpl(WorldGenerator(21,21).generate(), socket)
    val frame = View("Roguelike", controller)
    frame.isVisible = true

    val listener = ClientListener(socket, frame)
    listener.run()
}

fun convertX(value: Int): Double {
    return value.toDouble() / 1024
}

fun convertY(value: Int): Double {
    return value.toDouble() / 780
}