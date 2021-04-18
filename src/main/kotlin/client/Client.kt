package client

import client.controller.ControllerImpl
import client.controller.ControllerKeyListener
import client.view.View
import common.model.*
import utils.SocketWrapper
import java.net.Socket

fun main() {
    val socket = SocketWrapper(Socket("127.0.0.1", 8000))
    val controller = ControllerImpl(socket)

    val frame = View("Roguelike", World(21, 21))

    val keyboardListener = ControllerKeyListener(controller)
    frame.addKeyListener(keyboardListener)

    val listener = ClientListener(socket, frame)
    listener.run()
}