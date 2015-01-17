package com.turbosandwich

import java.io.InputStreamReader
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.InetAddress
import java.net.URL
import scala.util.Try
import scala.io.Source

package object net {
  
  def portHash(address: InetAddress): Int = ???
  
  def buildURL(address: InetAddress)(extension: String) = {
    new URL("http://" + address.getHostAddress + ":" + portHash(address) + extension)
  }
  
  def get[T](address: InetAddress, request: String)(handler: InputStream => T): Option[T] = {
    Try(buildURL(address)(request).openConnection().asInstanceOf[HttpURLConnection])
      .flatMap { connection => Try(connection.getInputStream) }
      .flatMap { reader =>
        val result = Try(handler(reader))
        reader.close()
        result
      }.toOption
  }
  
  def computeLocalIPAddress(): InetAddress = try {
    val response = Source.fromInputStream(new URL("http://www.curlmyip.com").openStream()).mkString
    InetAddress.getByName(response)
  } catch {
    case _: Throwable => InetAddress.getLocalHost
  }

}