package com.turbosandwich.client

import java.net.InetAddress
import java.util.Date
import java.io.Reader
import java.io.Writer
import com.turbosandwich.persistence.Unmarshaller
import com.turbosandwich.persistence.Marshaller

case class Peer(ipAddress: InetAddress, lastSeen: Date, available: Boolean) {
  def unavailable = Peer(ipAddress, lastSeen, false)
  
  def justSeen = Peer(ipAddress, new Date, true)
  
  def this(ipAddress: InetAddress) = this(ipAddress, new Date, true)
}

object Peer {
  type PeerSetUnmarshaller = Unmarshaller[Set[Peer]]
  type PeerSetMarshaller = Marshaller[Set[Peer]]
  
  def unmarshall(reader: Reader): Set[Peer] = ???
  
  def marshall(writer: Writer, peerSet: Set[Peer]): Unit = ???
}