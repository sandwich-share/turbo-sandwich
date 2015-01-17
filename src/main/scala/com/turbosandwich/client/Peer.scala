package com.turbosandwich.client

import java.net.InetAddress
import java.util.Date
import java.io.{OutputStream, InputStream}
import com.turbosandwich.persistence.Unmarshaller
import com.turbosandwich.persistence.Marshaller
import com.cognitect.transit.TransitFactory

import scala.collection.JavaConverters._

case class Peer(ipAddress: InetAddress, lastSeen: Date, available: Boolean) {
  def unavailable = Peer(ipAddress, lastSeen, false)
  
  def justSeen = Peer(ipAddress, new Date, true)
  
  def this(ipAddress: InetAddress) = this(ipAddress, new Date, true)
}

object Peer {
  type PeerSetUnmarshaller = Unmarshaller[Set[Peer]]
  type PeerSetMarshaller = Marshaller[Set[Peer]]
  
  def unmarshal(input: InputStream): Set[Peer] = {
    val reader = TransitFactory.reader(TransitFactory.Format.MSGPACK, input)
    return reader.read[Set[Peer]]()
  }
  
  def marshal(output: OutputStream, peerSet: Set[Peer]): Unit = {
    val writer = TransitFactory.writer[Set[Peer]](TransitFactory.Format.MSGPACK, output)
    writer.write(peerSet)
  }
}