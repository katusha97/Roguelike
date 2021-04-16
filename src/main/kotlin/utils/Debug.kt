package utils

import common.model.WorldProposal

fun WorldProposal.toStringConsole(): String {
    val stones = map.stones.map { Pair(it.x, it.y) }.toSet()
    println(player.x)
    println(player.y)
    val builder = StringBuilder()
    for (i in 1..map.sizeX) {
        for (j in 1..map.sizeY) {
            when {
                player.x == i && player.y == j -> builder.append("\uD83D\uDCA9")
                Pair(i, j) in stones -> builder.append("\uD83E\uDDF1")
                else -> builder.append("  ")
            }
        }
        builder.append("\n")
    }
    return builder.toString()
}
