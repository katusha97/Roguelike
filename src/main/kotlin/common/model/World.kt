package common.model

import kotlinx.serialization.Serializable

@Serializable
class World(val map: LevelStaticMap, val players: List<MovableGameObject>) {
    constructor(sizeX: Int, sizeY: Int) : this(
        LevelStaticMap(setOf(), sizeX, sizeY), ArrayList()
    )
}
