package client.controller

import common.protocol.commands.Move

interface Controller {
    fun move(direction: Move.Direction)
    fun shoot()
    fun pause()
    fun save()
    fun exit()
}