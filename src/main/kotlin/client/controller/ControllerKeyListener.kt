package client.controller

import common.protocol.commands.Direction
import common.protocol.commands.Move
import java.awt.event.KeyEvent
import java.awt.event.KeyListener

class ControllerKeyListener(private val controller: Controller) : KeyListener {
    override fun keyTyped(p0: KeyEvent?) {
    }

    override fun keyPressed(e: KeyEvent?) {
        if (e == null) {
            return
        }
        when (e.keyCode) {
            KeyEvent.VK_RIGHT -> controller.move(Direction.RIGHT)
            KeyEvent.VK_LEFT -> controller.move(Direction.LEFT)
            KeyEvent.VK_UP -> controller.move(Direction.UP)
            KeyEvent.VK_DOWN -> controller.move(Direction.DOWN)
            KeyEvent.VK_SPACE -> controller.shoot()
            KeyEvent.VK_ESCAPE -> controller.exit()
        }
    }

    override fun keyReleased(p0: KeyEvent?) {
    }
}