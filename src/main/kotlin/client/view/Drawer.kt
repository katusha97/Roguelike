package client.view

import common.model.*
import java.awt.Color
import java.awt.Graphics
import javax.swing.JPanel

class Drawer(private var world: World): JPanel() {

    private val sizeRect = 30
    private val sizePlayer = 20
    private val startX = 50
    private var id = -1

    override fun paintComponent(g: Graphics?) {
        super.paintComponent(g)
        if (g != null) {
            draw(g)
        }
    }

    fun updateWorld(newWorld: World, myId: Int) {
        world = newWorld
        id = myId
    }

    private fun draw(g: Graphics) {
        drawPlan(world.map, g)
        drawPlayer(world.players, g)
    }

    private fun drawPlayer(players: HashMap<Int, MovableGameObject>, g: Graphics) {
        for (player in players.values) {
            val startY = sizeRect * world.map.sizeY + 50
            val currX = (player.x - 1) * sizeRect + startX + (sizeRect / 2 - sizePlayer / 2)
            val currY = startY - (player.y - 2) * sizeRect - (sizeRect / 2 + sizePlayer / 2)
            if (player.id == id) {
                g.color = Color.RED
            }
            else {
                g.color = Color.GREEN
            }
            g.fillOval(currX, currY, sizePlayer, sizePlayer)
        }
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