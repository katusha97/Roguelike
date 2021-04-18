package client.view

import common.model.*
import java.awt.Color
import java.awt.Graphics
import javax.swing.JPanel

class Drawer(private val world: WorldProposal, private val myWidth: Int, private val myHeight: Int): JPanel() {

    private val sizeRect = 30;
    private val sizePlayer = 20;
    private var startX = 50
    private var startY = 50

    override fun paintComponent(g: Graphics?) {
        super.paintComponent(g)
        if (g != null) {
            draw(g)
        }
    }

    private fun draw(g: Graphics) {
        drawPlan(world.map, g)
        drawPlayer(world.player, g)
    }

    private fun drawPlayer(player: PlayerProposal, g: Graphics) {
        val currX = player.x * sizeRect + startX + (sizeRect / 2 - sizePlayer / 2)
        val currY = player.y * sizeRect + startY + (sizeRect / 2 - sizePlayer / 2)
        g.color = Color.RED
        g.fillOval(currX, currY, sizePlayer, sizePlayer)
    }

    private fun drawPlan(map: LevelStaticMapProposal, g: Graphics) {
        var x = startX
        var y = startY
        for (i in 0..map.sizeX) {
            for (j in 0..map.sizeY) {
                g.drawRect(x, y, sizeRect, sizeRect)
                x += sizeRect
            }
            y += sizeRect
            x = startX
        }
        y = startY
        for (stone in map.stones) {
            val currX = stone.x * sizeRect + x
            val currY = stone.y * sizeRect + y
            g.color = Color.BLUE
            g.fillRect(currX, currY, sizeRect, sizeRect)
        }
    }

    fun convertX(value: Double): Int {
        return (value * myWidth).toInt()
    }

    fun convertY(value: Double): Int {
        return (value * myHeight).toInt()
    }
}