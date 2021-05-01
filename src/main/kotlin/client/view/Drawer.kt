package client.view

import common.model.*
import java.awt.Color
import java.awt.Graphics
import javax.swing.JPanel

class Drawer(private var world: World): JPanel() {

    private val sizeRect = 30
    private val sizePlayer = 20
    private val startX = 50

    override fun paintComponent(g: Graphics?) {
        super.paintComponent(g)
        if (g != null) {
            draw(g)
        }
    }

    fun updateWorld(newWorld: World) {
        world = newWorld
    }

    private fun draw(g: Graphics) {
        drawPlan(world.map, g)
        drawPlayer(world.player, g)
        drawBots(world.bots, g)
    }

    private fun drawBots(bots: List<MovableGameObject>, g: Graphics) {
        for (bot in bots) {
            val startY = sizeRect * world.map.sizeY + 50
            val currX = (bot.x - 1) * sizeRect + startX + (sizeRect / 2 - sizePlayer / 2)
            val currY = startY - (bot.y - 2) * sizeRect - (sizeRect / 2 + sizePlayer / 2)
            if (bot is ActiveAngryBot) {
                g.color = Color.ORANGE
            } else if (bot is PassiveAngryBot) {
                g.color = Color.GREEN
            }
            g.fillOval(currX, currY, sizePlayer, sizePlayer)
        }
    }

    private fun drawPlayer(player: Player, g: Graphics) {
        val startY = sizeRect * world.map.sizeY + 50
        val currX = (player.x - 1) * sizeRect + startX + (sizeRect / 2 - sizePlayer / 2)
        val currY = startY - (player.y - 2) * sizeRect - (sizeRect / 2 + sizePlayer / 2)
        g.color = Color.RED
        g.fillOval(currX, currY, sizePlayer, sizePlayer)
    }

    private fun drawPlan(map: LevelStaticMap, g: Graphics) {
        val startY = sizeRect * world.map.sizeY + 50
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