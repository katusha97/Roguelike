package client.controller

import common.commands.Move
import common.model.World

interface Controller {
    val world: World
    fun move(direction: Move.Direction)
    fun shoot()
    fun pause()
    fun save()
    fun exit()
}