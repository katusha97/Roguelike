package common.model

import kotlinx.serialization.Serializable

class World(var levelPlan: LevelPlan, var player: Player, message: Message)

@Serializable
class WorldProposal(val map: LevelStaticMapProposal, val player: PlayerProposal)
