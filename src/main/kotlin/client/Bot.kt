package client

import client.controller.ControllerBotListener
import client.controller.ControllerImpl
import utils.ClientSocketWrapper
import java.net.Socket

fun main() {
    val socket = ClientSocketWrapper(Socket("127.0.0.1", 8000))
    val controller = ControllerImpl(socket)
    ControllerBotListener(controller).loop()
    val listener = BotListener(socket)
    listener.run()
}