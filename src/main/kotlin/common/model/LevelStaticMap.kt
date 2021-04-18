package common.model

import kotlinx.serialization.Serializable

@Serializable
class LevelStaticMap(val stones: Set<StaticGameObject>, val sizeX: Int, val sizeY: Int)
