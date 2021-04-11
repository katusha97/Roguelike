package client

import client.controller.ControllerImpl
import client.view.View
import common.model.*
import utils.SocketWrapper

fun main() {
    val list = ArrayList<Line>()
    list.add(Line(convertX(50), convertY(50), convertX(100), convertY(50)))
    list.add(Line(convertX(50), convertY(200), convertX(100), convertY(200)))
    list.add(Line(convertX(50), convertY(50), convertX(50), convertY(200)))
    list.add(Line(convertX(100), convertY(50), convertX(100), convertY(200)))
    val plan = LevelPlan(list)
    val player = Player(convertX(500), convertY(500), 0)
    val socket = SocketWrapper("127.0.0.1", 8000)
    val controller = ControllerImpl(World(plan, player, Message("")), socket)
    val frame = View("Roguelike", controller)
    frame.isVisible = true
}

fun convertX(value: Int): Double {
    return value.toDouble() / 1024
}

fun convertY(value: Int): Double {
    return value.toDouble() / 780
}