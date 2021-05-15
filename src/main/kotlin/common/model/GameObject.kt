package common.model

import kotlinx.serialization.Serializable

interface GameObject {
    val x: Int
    val y: Int
}

@Serializable
sealed class StaticGameObject : GameObject

@Serializable
sealed class MovableGameObject : GameObject {
    protected abstract var posX: Int
    protected abstract var posY: Int
    abstract val id: Int
    abstract val health: Int

    fun move(newPosX: Int, newPosY: Int) {
        posX = newPosX
        posY = newPosY
    }

    override val x: Int
        get() = posX
    override val y: Int
        get() = posY
}

@Serializable
class StoneItemProposal(override val x: Int, override val y: Int) : StaticGameObject()

@Serializable
class Player(override var posX: Int, override var posY: Int, override var health: Int, override val id: Int) :
    MovableGameObject()

@Serializable
class ActiveAngryBot(
    override var posX: Int, override var posY: Int,
    override var health: Int, val power: Int, override val id: Int
) : MovableGameObject()

@Serializable
class PassiveAngryBot(
    override var posX: Int, override var posY: Int,
    override var health: Int, val power: Int, override val id: Int
) : MovableGameObject()

// TODO:
// 3) Научить ботов реагировать на игроков (крест видимости, пример, 5 клеток строго вправо)
//     1) Чекнуть, есть ли игрок на кресте впоть до стен
//     2) Если есть, идём к нему (делаем всего один шаг в его сторону)
//     3) Если нет, то продолжаем рандомные блуждания

