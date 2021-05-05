package server

class PlayerRegister {
    private var targetID = 0

    fun getNewId() = targetID++
}