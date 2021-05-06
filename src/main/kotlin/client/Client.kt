package client

import client.controller.ControllerImpl
import client.controller.ControllerKeyListener
import client.view.View
import common.model.*
import utils.ClientSocketWrapper
import java.net.Socket

fun main() {
    val socket = ClientSocketWrapper(Socket("127.0.0.1", 8000))
    val controller = ControllerImpl(socket)

    val frame = View("Roguelike", World(21, 21))

    val keyboardListener = ControllerKeyListener(controller)
    frame.addKeyListener(keyboardListener)

    val listener = ClientListener(socket, frame);
    listener.run()
}