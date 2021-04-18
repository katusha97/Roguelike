package common.protocol.commands

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
            Direction.LEFT -> x -= 1
            Direction.RIGHT -> x += 1
            Direction.UP -> y += 1
            Direction.DOWN -> y -= 1
        }

        // TODO: Improve game object to stop this trash
        if (!currWorld.map.stones.any { it.x == x && it.y == y }) {
            currWorld.player.move(x, y)
        }
    }

    enum class Direction {
        RIGHT,
        LEFT,
        UP,
        DOWN
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
