package client.controller

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
            KeyEvent.VK_RIGHT -> controller.move(Move.Direction.RIGHT)
            KeyEvent.VK_LEFT -> controller.move(Move.Direction.LEFT)
            KeyEvent.VK_UP -> controller.move(Move.Direction.UP)
            KeyEvent.VK_DOWN -> controller.move(Move.Direction.DOWN)
            KeyEvent.VK_SPACE -> controller.shoot()
            KeyEvent.VK_ESCAPE -> controller.exit()
        }
    }

    override fun keyReleased(p0: KeyEvent?) {
    }
}