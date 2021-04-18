package utils

import common.model.World

fun World.toStringConsole(): String {
    val stones = map.stones.map { Pair(it.x, it.y) }.toSet()
    val builder = StringBuilder()
    for (y in map.sizeY downTo 1) {
        for (x in 1..map.sizeX) {
            when {
                player.x == x && player.y == y -> builder.append("\uD83D\uDCA9")
                Pair(x, y) in stones -> builder.append("\uD83E\uDDF1")
                else -> builder.append("  ")
            }
        }
        builder.append("\n")
    }
    return builder.toString()
}
