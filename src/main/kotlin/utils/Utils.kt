package utils

import common.model.World

fun World.toStringConsole(): String {
    val stones = map.stones.keys.toSet()
    val builder = StringBuilder()
    for (y in map.sizeY downTo 1) {
        for (x in 1..map.sizeX) {
            when {
                Pair(x, y) in stones -> builder.append("\uD83E\uDDF1")
                getMovableGameObjectsOnMap(x, y).isNotEmpty() -> builder.append("\uD83D\uDCA9")
                else -> builder.append("  ")
            }
        }
        builder.append("\n")
    }
    return builder.toString()
}
