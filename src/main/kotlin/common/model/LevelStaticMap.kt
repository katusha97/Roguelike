package common.model

import kotlinx.serialization.Serializable

@Serializable
class LevelStaticMap(val stones: MutableMap<Pair<Int, Int>, StaticGameObject>, val sizeX: Int, val sizeY: Int) {
    fun cellIsEmpty(x: Int, y: Int) = !stones.contains(Pair(x, y))
}
