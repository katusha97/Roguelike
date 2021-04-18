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

    fun updateWorld(newWorld: World) {
        viewWorld.updateWorld(newWorld)
        repaint()
    }

    private fun createUI() {
        title = nameOfFrame
        setSize(1024, 768)
        add(viewWorld)
    }
}
