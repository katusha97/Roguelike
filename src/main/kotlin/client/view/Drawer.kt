package client.view

import client.controller.Controller
import client.controller.ControllerImpl
import common.model.*
import java.awt.Color
import java.awt.Graphics
import javax.swing.JPanel

class Drawer(private val controller: Controller): JPanel() {

    private val sizeRect = 30
    private val sizePlayer = 20
    private val startX = 50

    override fun paintComponent(g: Graphics?) {
        println("paintComponent")
        super.paintComponent(g)
        if (g != null) {
            draw(g)
        }
    }

    private fun draw(g: Graphics) {
        val world = controller.world
        drawPlan(world.map, g)
        drawPlayer(world.player, g)
    }

    private fun drawPlayer(player: PlayerProposal, g: Graphics) {
        val startY = sizeRect * controller.world.map.sizeY + 50
        val currX = (player.x - 1) * sizeRect + startX + (sizeRect / 2 - sizePlayer / 2)
        val currY = startY - (player.y - 1) * sizeRect - (sizeRect / 2 + sizePlayer / 2)
        g.color = Color.RED
        g.fillOval(currX, currY, sizePlayer, sizePlayer)
    }

    private fun drawPlan(map: LevelStaticMapProposal, g: Graphics) {
        val startY = sizeRect * controller.world.map.sizeY + 50
        var x = startX
        var y = startY
        for (i in 0 until map.sizeX) {
            for (j in 0 until map.sizeY) {
                g.drawRect(x, y, sizeRect, sizeRect)
                x += sizeRect
            }
            y -= sizeRect
            x = startX
        }
        y = startY
        for (stone in map.stones) {
            val currX = x + (stone.x - 1)  * sizeRect
            val currY = y - (stone.y - 1) * sizeRect
            g.color = Color.BLUE
            g.fillRect(currX, currY, sizeRect, sizeRect)
        }
    }
}