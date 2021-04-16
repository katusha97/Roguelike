package common.protocol.commands

import common.model.World
import common.model.WorldProposal
import kotlinx.serialization.Serializable

@Serializable
sealed class Action {

    abstract fun getName(): String

    abstract fun execute(currWorld: WorldProposal)
}

@Serializable
class Move(private val direction: Direction): Action() {

    override fun getName(): String {
        return "move"
    }

    override fun execute(currWorld: WorldProposal) {
        var x = currWorld.player.x
        var y = currWorld.player.y
        when (direction) {
            Direction.left -> x -= 1
            Direction.right -> x += 1
            Direction.up -> y += 1
            Direction.down -> y -= 1
        }
        currWorld.player.move(x, y)
    }

    enum class Direction {
        right,
        left,
        up,
        down
    }
}


@Serializable
class Shoot: Action() {

    override fun getName(): String {
        return "shoot"
    }

    override fun execute(currWorld: WorldProposal) {
        TODO("Not yet implemented")
    }
}

@Serializable
class Exit: Action() {

    override fun getName(): String {
        return "exit"
    }

    override fun execute(currWorld: WorldProposal) {
        TODO("Not yet implemented")
    }
}
