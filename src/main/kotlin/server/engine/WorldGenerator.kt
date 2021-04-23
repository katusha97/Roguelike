package server.engine

import common.model.*
import common.protocol.commands.Move

class WorldGenerator(private val sizeX: Int, private val sizeY: Int) {
    init {
        assert(sizeX % 2 == 1 && sizeY % 2 == 1)
    }

    data class Point(val x: Int, val y: Int)

    fun generateWorld(): World {
        val stones = generateStones()

        return World(
            LevelStaticMap(
                stones.map {
                    StoneItemProposal(it.x, it.y)
                }.toSet(),
                sizeX,
                sizeY
            ),
            generatePlayer(), generateBot(stones)
        )
    }

    private fun generatePlayer(): Player {
        return Player(2, 2, 100)
    }

    private fun generateBot(stones: Set<Point>): List<MovableGameObject> {
        //тут надо в зависимости от уровня генерировать больше или меньше ботов
        //сейчас просто сделаю для примера несколько
        val list = ArrayList<MovableGameObject>()
        val random = java.util.Random()
        var countActive = 0
        var countPassive = 0
        while (countActive != 5) {
            val x = random.ints(1, 2, sizeX ).sum()
            val y = random.ints(1, 2, sizeY).sum()
            if (!stones.any { it.x == x && it.y == y } && x != 2 && y != 2) {
                list.add(
                    ActiveAngryBot(
                        x,
                        y,
                        100,
                        10,
                        arrayListOf(Move(Move.Direction.RIGHT), Move(Move.Direction.RIGHT))
                    )
                )
                countActive++
            }
        }
        while (countPassive != 5) {
            val x = random.ints(1, 2, sizeX).sum()
            val y = random.ints(1, 2, sizeY).sum()
            if (!stones.any { it.x == x && it.y == y } && x != 2 && y != 2) {
                list.add(
                    PassiveAngryBot(
                        x,
                        y,
                        100,
                        10,
                        arrayListOf(Move(Move.Direction.LEFT), Move(Move.Direction.LEFT))
                    )
                )
                countPassive++
            }
        }
        return list
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
