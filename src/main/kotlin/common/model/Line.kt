package common.model

import kotlinx.serialization.Serializable

class Line(val x1: Double, val y1: Double, val x2: Double, val y2: Double) {
}

@Serializable
class StoneItemProposal(val x: Int, val y: Int)
