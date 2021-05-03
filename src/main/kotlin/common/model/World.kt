package common.model

import kotlinx.serialization.Serializable

@Serializable
class World(val map: LevelStaticMap, val players: HashMap<Int, MovableGameObject>) {
    constructor(sizeX: Int, sizeY: Int) : this(
        LevelStaticMap(setOf(), sizeX, sizeY), HashMap()
    )
}
