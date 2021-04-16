package common.protocol.commands

import common.model.World
import kotlinx.serialization.Serializable

@Serializable
sealed class Action {

    abstract fun getName(): String

    abstract fun execute(currWorld: World): World
}

@Serializable
class Move(private val direction: Direction): Action() {

    override fun getName(): String {
        return "move"
    }

    override fun execute(currWorld: World): World {
        TODO("Not yet implemented")
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

    override fun execute(currWorld: World): World {
        TODO("Not yet implemented")
    }
}

@Serializable
class Exit: Action() {

    override fun getName(): String {
        return "exit"
    }

    override fun execute(currWorld: World): World {
        TODO("Not yet implemented")
    }
}
