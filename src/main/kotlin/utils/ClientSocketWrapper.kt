package utils

import java.io.*
import java.net.InetAddress
import java.net.Socket

class ClientSocketWrapper(val socket: Socket) {
    private val reader: BufferedReader = createReader()
    private val writer: PrintStream = createWriter()

    val port: Int = socket.localPort
    val host: InetAddress = socket.inetAddress

    private fun createReader(): BufferedReader {
        return BufferedReader(InputStreamReader(socket.getInputStream()))
    }

    private fun createWriter(): PrintStream {
        return PrintStream(socket.getOutputStream())
    }

    fun writeLine(line: String) {
        writer.println(line)
    }

    fun readLine(): String {
        return reader.readLine()
    }
}