package common.model

import kotlinx.serialization.Serializable

@Serializable
class World(val map: LevelStaticMap, val player: Player, val bots: List<MovableGameObject>) {
    constructor(sizeX: Int, sizeY: Int) : this(
        LevelStaticMap(setOf(), sizeX, sizeY),
        Player(0, 0, 0), ArrayList()
    )
}
