package com.turbosandwich.server

import java.net.InetAddress
import com.turbosandwich.files.FileEntry
import com.turbosandwich.files.FileManager
import com.turbosandwich.files.SimpleQuery
import java.nio.file.Paths

class ServerImpl(ipAddress: InetAddress, port: Int, fileManager: FileManager) extends AbstractHttpServer(ipAddress, port) {
  
  override def handleRequest = {
    case "/ping" => {
      request: Request => request.responseBodyWriter.map { writer => writer.append("pong\n") }
    }
    
    case "/query" => {
      request: Request => request.responseBody.map { writer =>
        val marshaller = FileEntry.marshal(writer)_
        request.responseQuery.foreach { query => marshaller(fileManager.query(SimpleQuery(query))) }
      }
    }
    
    case "/file" => {
      request: Request => request.responseBody.map { outputStream =>
        request.responsePath.foreach { rawPath =>
          val path = Paths.get(rawPath)
          fileManager.fetchFile(path.subpath(1, path.getNameCount - 1), outputStream)
        }
      }
    }
  }

}