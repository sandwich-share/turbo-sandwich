package com.turbosandwich.server

import java.net.InetAddress

class ServerImpl(ipAddress: InetAddress, port: Int) extends AbstractHttpServer(ipAddress, port) {
  
  override def handleRequest = {
    case "/ping" => {
      request: Request => request.responseBody.map { writer => writer.append("pong\n") }
    }
  }

}