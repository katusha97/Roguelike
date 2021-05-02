package common.protocol.commands

import kotlinx.serialization.Serializable

@Serializable
sealed class ActionPlayer {
    abstract fun getName(): String
}

@Serializable
class MoveFromPlayer(private val direction: Direction): ActionPlayer() {

    override fun getName(): String {
        return "move"
    }
}

@Serializable
class ShootFromPlayer: ActionPlayer() {

    override fun getName(): String {
        return "shoot"
    }
}

@Serializable
class ExitFromPlayer: ActionPlayer() {

    override fun getName(): String {
        return "exit"
    }
}
