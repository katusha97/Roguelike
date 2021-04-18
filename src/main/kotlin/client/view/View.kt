package client.view

import client.controller.Controller
import common.protocol.commands.Move
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import javax.swing.JFrame

class View(private val nameOfFrame: String, private var controller: Controller) : JFrame(), KeyListener {
    private var viewWorld: Drawer = Drawer(controller)

    init {
        viewWorld.isVisible = true
        createUI()
    }

    private fun createUI() {
        title = nameOfFrame
        setSize(1024, 768)
        add(viewWorld)
        addKeyListener(this)
    }


    override fun keyTyped(e: KeyEvent?) {

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

    override fun keyReleased(e: KeyEvent?) {

    }
}
