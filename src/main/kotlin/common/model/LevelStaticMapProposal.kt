package common.model

import kotlinx.serialization.Serializable

@Serializable
class LevelStaticMapProposal(val stones: Set<StaticGameObject>, val sizeX: Int, val sizeY: Int)
