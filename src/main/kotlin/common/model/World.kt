package common.model

import kotlinx.serialization.Serializable

@Serializable
class World(val map: LevelStaticMap, val playersById: HashMap<Int, MovableGameObject>,
            val playersOnMap: HashMap<Int, HashSet<MovableGameObject>>) {
    constructor(sizeX: Int, sizeY: Int) : this(
        LevelStaticMap(setOf(), sizeX, sizeY), HashMap(), HashMap()
    )
}
