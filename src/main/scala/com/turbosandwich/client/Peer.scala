package com.turbosandwich.client

import java.net.InetAddress
import java.util.Date
import java.io.Reader
import java.io.Writer

case class Peer(ipAddress: InetAddress, lastSeen: Date, available: Boolean) {
  def unavailable = Peer(ipAddress, lastSeen, false)
  
  def justSeen = Peer(ipAddress, new Date, true)
  
  def this(ipAddress: InetAddress) = this(ipAddress, new Date, true)
}

object Peer {
  type PeerSetUnmarshaller = Reader => Set[Peer]
  type PeerSetMarshaller = Set[Peer] => Writer
  
  def unmarshall(reader: Reader): Set[Peer] = ???
  
  def marshall(peerSet: Set[Peer]): Writer = ???
}