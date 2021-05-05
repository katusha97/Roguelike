package client.controller

import common.protocol.commands.Direction
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.runBlocking

class ControllerBotListener(private val controller: Controller) {
    fun loop() {
        runBlocking {
            while (isActive) {
                val next = (0..3).random()
                println(next)
                when (next) {
                    0 -> controller.move(Direction.RIGHT)
                    1 -> controller.move(Direction.LEFT)
                    2 -> controller.move(Direction.UP)
                    3 -> controller.move(Direction.DOWN)
                }
                delay(500)
                loop()
            }
        }
    }
}