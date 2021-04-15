package common.model

import kotlinx.serialization.Serializable

@Serializable
sealed class GameObject {
    abstract val x: Int
    abstract val y: Int
}

@Serializable
open class StaticGameObject(override val x: Int, override val y: Int) : GameObject()

@Serializable
open class MovableGameObject(private var posX: Int, private var posY: Int): GameObject() {
    override val x: Int
        get() = posX

    override val y: Int
        get() = posX

    fun move(newPosX: Int, newPosY: Int) {
        posX = newPosX
        posY = newPosY
    }
}
