package utils

import java.io.*
import java.net.ServerSocket
import java.net.Socket

class SocketWrapper: Closeable {

    private var socket: Socket
    private var reader: BufferedReader
    private var writer: BufferedWriter

    constructor(ip: String, port: Int) {
        socket = Socket(ip, port)
        reader = createReader()
        writer = createWriter()
    }

    constructor(server: ServerSocket) {
        socket = server.accept()
        reader = createReader()
        writer = createWriter()
    }

    private fun createReader(): BufferedReader {
        return BufferedReader(InputStreamReader(socket.getInputStream()))
    }

    private fun createWriter(): BufferedWriter {
       return BufferedWriter(OutputStreamWriter(socket.getOutputStream()))
    }

    fun writeLine(line: String) {
        writer.write(line)
        writer.newLine()
        writer.flush()
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