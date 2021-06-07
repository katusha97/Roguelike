import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.int
import io.ktor.network.selector.*
import io.ktor.network.sockets.*
import kotlinx.coroutines.Dispatchers
import java.net.InetSocketAddress
import java.net.Socket

class Server: CliktCommand() {
    private val host: String by option(help = "Host address").default("localhost")
    private val port: Int by option(help = "Port number").int().default(8000)

    override fun run() {
        val serverSocket =
            aSocket(ActorSelectorManager(Dispatchers.IO))
            .tcp()
            .bind(InetSocketAddress(host, port))
        val server = server.Server(serverSocket)
        server.start()
    }
}

class Client: CliktCommand() {
    private val host: String by option(help = "Host address").default("localhost")
    private val port: Int by option(help = "Port number").int().default(8000)

    override fun run() {
        val clientSocket = Socket(host, port)
        val client = client.Client(clientSocket)
        client.start()
    }
}

class Start: CliktCommand(allowMultipleSubcommands = true) {
    override fun run() {}
}

fun main(args: Array<String>) {
    Start().subcommands(
        Server(),
        Client()
    ).main(args)
}
