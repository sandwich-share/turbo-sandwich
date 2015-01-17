package com.turbosandwich

import java.io.InputStreamReader
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.InetAddress
import java.net.URL
import scala.util.Try
import scala.io.Source
import java.net.Inet4Address
import java.security.MessageDigest
import java.net.Inet6Address

package object net {
  
  def unsign(signed: Byte): Int = if(signed >= 0) {
    signed
  } else {
    signed + 256
  }

  def portHash(ipAddress: InetAddress): Int = {
    var hash = Array[Byte]()
    var address = Array[Byte]()
    var port: Int = 0
    val messageDigest = MessageDigest.getInstance("MD5")
    ipAddress match {
      case ip: Inet4Address => {
        address = new Array[Byte](ip.getAddress.size + 12)
        System.arraycopy(ip.getAddress, 0, address, 12, ip.getAddress.size)
        address(10) = 0xff.asInstanceOf[Byte]
        address(11) = 0xff.asInstanceOf[Byte]
      }
      case ip: Inet6Address => {
        address = ip.getAddress
      }
    }
    hash = messageDigest.digest(address)
    port = (unsign(hash(0)) + unsign(hash(3))) << 8
    port += unsign(hash(1)) + unsign(hash(2))
    port &= 0xFFFF
    if(port < 1024) {
      port + 1024
    } else {
      port
    }
  }
  
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