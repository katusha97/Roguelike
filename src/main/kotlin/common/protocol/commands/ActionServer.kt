package common.protocol.commands

import common.model.World
import kotlinx.serialization.Serializable

@Serializable
sealed class ActionServer {

    abstract fun getName(): String

    abstract fun execute(currWorld: World)
}

@Serializable
class Move(private val direction: Direction): ActionServer() {

    override fun getName(): String {
        return "move"
    }

    override fun execute(currWorld: World) {
        // тут нужно как-то использовать id
//        var x = currWorld.player.x
//        var y = currWorld.player.y
//        when (direction) {
//            Direction.LEFT -> x -= 1
//            Direction.RIGHT -> x += 1
//            Direction.UP -> y += 1
//            Direction.DOWN -> y -= 1
//        }
//
//        // TODO: Improve game object to stop this trash
//        if (!currWorld.map.stones.any { it.x == x && it.y == y }) {
//            currWorld.player.move(x, y)
//        }
    }
}


@Serializable
class Shoot: ActionServer() {

    override fun getName(): String {
        return "shoot"
    }

    override fun execute(currWorld: World) {
        TODO("Not yet implemented")
    }
}

@Serializable
class Exit: ActionServer() {

    override fun getName(): String {
        return "exit"
    }

    override fun execute(currWorld: World) {
        TODO("Not yet implemented")
    }
}
