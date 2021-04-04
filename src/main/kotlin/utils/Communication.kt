package utils

import java.io.*
import java.net.InetAddress
import java.net.Socket

class Communication(private val socket: Socket): Closeable {
    private val reader: BufferedReader = createReader()
    private val writer: PrintStream = createWriter()

    val port: Int = socket.port
    val host: InetAddress = socket.inetAddress

    constructor(ip: String, port: Int) : this(Socket(ip, port))

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

    override fun close() {
        socket.close()
        reader.close()
        writer.close()
    }
}