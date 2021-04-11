package client.view

import common.model.Line
import common.model.Player
import common.model.World
import java.awt.Color
import java.awt.Graphics
import javax.swing.JPanel

class Drawer(private val world: World, private val myWidth: Int, private val myHeight: Int): JPanel() {

    override fun paintComponent(g: Graphics?) {
        super.paintComponent(g)
        if (g != null) {
            draw(g)
        }
    }

    fun draw(g: Graphics) {
        drawPlan(world.levelPlan.lines, g)
        drawPlayer(world.player, g)
    }

    private fun drawPlayer(player: Player, g: Graphics) {
        g.color = Color.RED
        val diameter = myWidth / 80
        g.fillOval(convertX(player.coordX), convertY(player.coordY), diameter, diameter)
    }

    private fun drawPlan(lines: ArrayList<Line>, g: Graphics) {
        for (l in lines) {
            g.drawLine(convertX(l.x1), convertY(l.y1), convertX(l.x2), convertY(l.y2))
        }
    }

    fun convertX(value: Double): Int {
        return (value * myWidth).toInt()
    }

    fun convertY(value: Double): Int {
        return (value * myHeight).toInt()
    }
}