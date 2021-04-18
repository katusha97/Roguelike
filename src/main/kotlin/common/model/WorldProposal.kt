package common.model

import kotlinx.serialization.Serializable

@Serializable
class WorldProposal(val map: LevelStaticMapProposal, val player: PlayerProposal)
