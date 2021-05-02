package utils

import io.ktor.network.sockets.*
import io.ktor.util.network.*
import io.ktor.utils.io.*
import io.ktor.utils.io.jvm.javaio.*
import java.io.PrintStream


class ServerSocketWrapper(val socket: Socket) {
    private val reader: ByteReadChannel = createReader()
    val writer: PrintStream = createWriter()

    val port = socket.localAddress.port
    val host = socket.localAddress.hostname

    private fun createReader(): ByteReadChannel {
        return socket.openReadChannel()
    }

    private fun createWriter(): PrintStream {
        return PrintStream(socket.openWriteChannel(autoFlush = true).toOutputStream(), true)
    }

    fun writeLine(line: String) {
        writer.println(line)
    }

    suspend fun readLine(): String {
        return reader.readUTF8Line() ?: ""
    }
}