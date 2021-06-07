package client

import client.controller.ControllerImpl
import client.controller.ControllerKeyListener
import client.view.View
import common.model.*
import utils.ClientSocketWrapper
import java.net.Socket
import kotlin.system.exitProcess

class Client(private val socket: Socket) {
    fun start() {
        val socket = ClientSocketWrapper(socket)
        val controller = ControllerImpl(socket)

        val frame = View("Roguelike", World(21, 21))

        val keyboardListener = ControllerKeyListener(controller)
        frame.addKeyListener(keyboardListener)

        val listener = ClientListener(socket, frame)
        listener.run()
        exitProcess(0)
    }
}