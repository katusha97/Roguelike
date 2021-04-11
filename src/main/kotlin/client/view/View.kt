package client.view

import client.controller.Controller
import common.commands.Move
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import javax.swing.JFrame

class View(private val nameOfFrame: String, private val controller: Controller) : JFrame(), KeyListener {

    init {
        createUI()
    }

    private fun createUI() {
        title = nameOfFrame
        setSize(1024, 768)
        val viewWorld = Drawer(controller.world, 1024, 768)
        viewWorld.isVisible = true
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
            KeyEvent.VK_RIGHT -> controller.move(Move.Direction.right)
            KeyEvent.VK_LEFT -> controller.move(Move.Direction.left)
            KeyEvent.VK_UP -> controller.move(Move.Direction.up)
            KeyEvent.VK_DOWN -> controller.move(Move.Direction.down)
            KeyEvent.VK_SPACE -> controller.shoot()
        }
    }

    override fun keyReleased(e: KeyEvent?) {

    }
}
