package utils

import common.model.WorldProposal

fun WorldProposal.toStringConsole(): String {
    val stones = map.stones.map { Pair(it.x, it.y) }.toSet()

    val builder = StringBuilder()
    for (i in 1..map.sizeX) {
        for (j in 1..map.sizeY) {
            when {
                Pair(i, j) in stones -> builder.append("*")
                else -> builder.append(" ")
            }
        }
        builder.append("\n")
    }
    return builder.toString()
}
