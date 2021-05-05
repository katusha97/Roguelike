package server.engine

import common.protocol.commands.Direction

class Cell(val x: Int, val y: Int)

fun getNeighboursCells(x: Int, y: Int): List<Cell> {
    return listOf(
        Cell(x+1, y),
        Cell(x-1, y),
        Cell(x, y+1),
        Cell(x, y-1),
    )
}

fun getNeighboursCellsWithDirection(x: Int, y: Int): List<Pair<Cell, Direction>> {
    return getNeighboursCells(x, y)
        .zip(listOf(
            Direction.RIGHT,
            Direction.LEFT,
            Direction.UP,
            Direction.DOWN
        )
    )
}

fun revertedDirection(d: Direction): Direction {
    return when (d) {
        Direction.RIGHT -> Direction.LEFT
        Direction.LEFT -> Direction.RIGHT
        Direction.UP -> Direction.DOWN
        Direction.DOWN -> Direction.UP
    }
}
