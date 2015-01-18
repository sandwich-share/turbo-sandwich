package com.turbosandwich.files

import java.net.InetAddress
import java.io.OutputStream
import java.io.InputStream

case class QueryResponse(ipAddress: InetAddress, fileSet: Set[FileEntry])

object QueryResponse {
  def marshal(outputStream: OutputStream)(queryResponse: QueryResponse): Unit = ???
  
  def unmarshal(inputStream: InputStream): QueryResponse = ???
}