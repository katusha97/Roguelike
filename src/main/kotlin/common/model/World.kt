package common.model

import kotlinx.serialization.Serializable

@Serializable
class World(val map: LevelStaticMap, val playersById: MutableMap<Int, MovableGameObject>,
            val playersOnMap: MutableMap<Pair<Int, Int>, HashSet<MovableGameObject>>) {

    constructor(sizeX: Int, sizeY: Int) : this(
        LevelStaticMap(setOf(), sizeX, sizeY), mutableMapOf(), mutableMapOf()
    )
}
