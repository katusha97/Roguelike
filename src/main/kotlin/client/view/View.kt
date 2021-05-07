package client.view

import common.model.World
import javax.swing.JFrame

class View(private val nameOfFrame: String, world: World) : JFrame() {
    private val viewWorld: Drawer = Drawer(world)

    init {
        viewWorld.isVisible = true
        isVisible = true
        createUI()
    }

    fun updateWorld(newWorld: World, myId: Int) {
        viewWorld.updateWorld(newWorld, myId)
        repaint()
    }

    private fun createUI() {
        title = nameOfFrame
        setSize(1024, 1024)
        add(viewWorld)
    }
}
