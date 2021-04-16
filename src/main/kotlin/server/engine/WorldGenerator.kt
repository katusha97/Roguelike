package server.engine

import common.model.LevelStaticMapProposal
import common.model.PlayerProposal
import common.model.StoneItemProposal
import common.model.WorldProposal

class WorldGenerator {
    fun generateWorld(): WorldProposal {
        return WorldProposal(
            LevelStaticMapProposal(
                setOf(
                    StoneItemProposal(1, 1),
                    StoneItemProposal(1, 2),
                    StoneItemProposal(2, 1),
                    StoneItemProposal(5, 5),
                    StoneItemProposal(4, 5),
                    StoneItemProposal(5, 4),
                ),
                5, 5
            ),
            PlayerProposal(3, 3, 100)
        )
    }
}