package com.turbosandwich

import java.io.InputStreamReader
import java.io.Reader
import java.net.HttpURLConnection
import java.net.InetAddress
import java.net.URL

import scala.util.Try

package object net {
  
  def portHash(address: InetAddress): Int = ???
  
  def buildURL(address: InetAddress)(extension: String) = {
    new URL("http://" + address.getHostAddress + ":" + portHash(address) + extension)
  }
  
  def get[T](address: InetAddress, request: String)(handler: Reader => T): Option[T] = {
    Try(buildURL(address)(request).openConnection().asInstanceOf[HttpURLConnection])
      .flatMap { connection => Try(new InputStreamReader(connection.getInputStream)) }
      .flatMap { reader =>
        val result = Try(handler(reader))
        reader.close()
        result
      }.toOption
  }

}