package common.model

import kotlinx.serialization.Serializable

@Serializable
class World(val map: LevelStaticMap, val playersById: MutableMap<Int, MovableGameObject>,
            val playersOnMap: MutableMap<Pair<Int, Int>, HashSet<MovableGameObject>>) {

    constructor(sizeX: Int, sizeY: Int) : this(
        LevelStaticMap(mutableMapOf(), sizeX, sizeY), mutableMapOf(), mutableMapOf()
    )

    fun getPlayersOnMap(x: Int, y: Int): HashSet<MovableGameObject> {
        return playersOnMap.computeIfAbsent(Pair(x, y)) { HashSet() }
    }

    fun addPlayer(player: Player) {
        playersById[player.id] = player
        playersOnMap.computeIfAbsent(Pair(player.x, player.y)) { HashSet() }.add(player)
    }

    fun movePlayerTo(id: Int, x: Int, y: Int) {
        val player: MovableGameObject = playersById[id] ?: return

        getPlayersOnMap(player.x, player.y).remove(player)
        player.move(x, y)
        getPlayersOnMap(x, y).add(player)
    }
}
