package common.model

import kotlinx.serialization.Serializable

class LevelPlan(var lines: ArrayList<Line>)

@Serializable
class LevelStaticMapProposal(val stones: Set<StaticGameObject>, val sizeX: Int, val sizeY: Int)
