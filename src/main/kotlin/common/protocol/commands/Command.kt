package common.protocol.commands

import common.model.World
import kotlinx.serialization.Serializable

@Serializable
sealed class Command

@Serializable
sealed class ServerCommand : Command()

@Serializable
class UpdateWorld(val world: World) : ServerCommand()

@Serializable
class InitializeWorld(val world: World) : ServerCommand()

@Serializable
class ExitAccept : ServerCommand()

@Serializable
sealed class ClientCommand : Command()

@Serializable
class ActionRequest(val action: ActionPlayer): ClientCommand()
