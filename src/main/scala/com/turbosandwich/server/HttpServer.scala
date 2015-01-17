package com.turbosandwich.server

import com.sun.net.httpserver
import com.sun.net.httpserver.{HttpExchange, HttpHandler}
import java.io.OutputStreamWriter
import java.net.InetAddress
import java.net.InetSocketAddress
import java.nio.file.Paths
import com.turbosandwich.util.using
import scala.io.Source
import scala.util.Try

abstract class AbstractHttpServer(ipAddress: InetAddress, port: Int) {
  private val server = httpserver.HttpServer.create(new InetSocketAddress(ipAddress, port), -1)
  server.createContext("/", new HttpHandler {
    override def handle(exchange: HttpExchange) {
      var path = Paths.get(exchange.getRequestURI.getPath)
      while (!handleRequest.isDefinedAt(path.toString())) {
        path = path.getParent
        if (path == null) {
          // TODO(Brendan): Should respond with 404.
          exchange.close()
          return
        }
      }
      handleRequest(path.toString())
      exchange.close()
    }
  })
  
  def start = server.start()
  
  def stop = server.stop(15)
  
  def handleRequest: PartialFunction[String, Request => Unit]
  
  class Request(private val exchange: HttpExchange) {
    def requestBody = Try(Source.fromInputStream(exchange.getRequestBody).mkString)
    def responseBody = Try(exchange.getResponseBody)
    def responseBodyWriter = responseBody.map { outStream => new OutputStreamWriter(outStream) }
    def responseQuery = Try(exchange.getRequestURI.getQuery)
  }
}