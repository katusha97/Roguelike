package server.engine

import common.model.LevelStaticMapProposal
import common.model.PlayerProposal
import common.model.StoneItemProposal
import common.model.WorldProposal

class WorldGenerator(private val sizeX: Int, private val sizeY: Int) {
    init {
        assert(sizeX % 2 == 1 && sizeY % 2 == 1)
    }

    data class Point(val x: Int, val y: Int)

    fun generate(): WorldProposal {
        val stones = generateStones()

        return WorldProposal(
            LevelStaticMapProposal(
                stones.map {
                    StoneItemProposal(it.x, it.y)
                }.toSet(),
                sizeX,
                sizeY
            ),
            PlayerProposal(2, 2, 100)
        )
    }

    private fun neighbours(point: Point) =
        listOf(
            Point(point.x + 2, point.y),
            Point(point.x - 2, point.y),
            Point(point.x, point.y + 2),
            Point(point.x, point.y - 2)
        )
            .filter { it.x in 1..sizeX && it.y in 1..sizeY }

    private fun randomDFS(current: Point, stones: MutableSet<Point>) {
        stones.remove(current)

        val nextPoints = neighbours(current).shuffled()

        for (next in nextPoints) {
            if (next in stones) {
                val between = Point((current.x + next.x) / 2, (current.y + next.y) / 2)
                stones.remove(between)
                randomDFS(next, stones)
            }
        }
    }

    private fun removeSomeBarrierStones(count: Int, stones: MutableSet<Point>) {
        val barriers =
            stones
                .filter { it.x in 2 until sizeX && it.y in 2 until sizeY }
                .filter {
                    neighbours(it).filter { it in stones }.count() == 2
                }
                .shuffled()
                .take(count)
        stones.removeAll(barriers)
    }

    private fun generateStones(): Set<Point> {
        val stones = mutableSetOf<Point>()
        for (x in 1..sizeX) {
            for (y in 1..sizeY) {
                stones.add(Point(x, y))
            }
        }

        val x = (2..sizeX step 2).toList().random()
        val y = (2..sizeY step 2).toList().random()

        randomDFS(Point(x, y), stones)
        removeSomeBarrierStones((sizeX + sizeY) / 5, stones)

        return stones
    }
}
