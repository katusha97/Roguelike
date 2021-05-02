package client.controller

import common.protocol.commands.Direction
import common.protocol.commands.Move
import kotlinx.coroutines.*
import kotlin.random.Random

class ControllerBotListener(private val controller: Controller) {
    fun loop() {
        val random = Random(0)
        CoroutineScope(Dispatchers.IO).launch {
            while (isActive) {
                when(random.nextInt(0, 4)) {
                    0 -> controller.move(Direction.RIGHT)
                    1 -> controller.move(Direction.LEFT)
                    2 -> controller.move(Direction.UP)
                    3 -> controller.move(Direction.DOWN)
                }
                delay(5000)
                loop()
            }
        }
    }
}