package client.controller

import common.protocol.commands.Move
import common.model.WorldProposal

interface Controller {
    var world: WorldProposal
    fun move(direction: Move.Direction)
    fun shoot()
    fun pause()
    fun save()
    fun exit()
}