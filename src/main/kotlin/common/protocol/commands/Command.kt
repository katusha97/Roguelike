package common.protocol.commands

import common.model.WorldProposal
import kotlinx.serialization.Serializable

@Serializable
sealed class Command

@Serializable
sealed class ServerCommand : Command()

@Serializable
class UpdateWorld(val world: WorldProposal) : ServerCommand()

@Serializable
class InitializeWorld(val world: WorldProposal) : ServerCommand()

@Serializable
class ExitAccept : ServerCommand()

@Serializable
sealed class ClientCommand : Command()

@Serializable
class ActionRequest(val action: Action): ClientCommand()
