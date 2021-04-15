package common.model

import kotlinx.serialization.Serializable

class Player (var coordX: Double, var coordY: Double, var health: Int) {
}

@Serializable
class PlayerProposal(var x: Int, var y: Int, var health: Int)