package client.controller

import common.protocol.commands.Direction

interface Controller {
    fun move(direction: Direction)
    fun shoot()
    fun pause()
    fun save()
    fun exit()
}