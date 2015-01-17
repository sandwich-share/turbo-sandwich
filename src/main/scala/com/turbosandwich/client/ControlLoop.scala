package com.turbosandwich.client

import scala.util.Random
import scala.io.Source
import com.turbosandwich.client.Peer.{
  PeerSetUnmarshaller,
  unmarshal
}
import com.turbosandwich.net.get

class ControlLoop {
  final val MAX_SIZE = 100
  private var peerSet = Set[Peer]()
  
  def run: Unit = {
    while (true) {
      val peer = peerSet.minBy { peer => peer.lastSeen }
      downloadRemotePeerSet(peer, unmarshal)
        .map { remoteSet => mergeLocalAndRemote(peer, peerSet, remoteSet) }
        .foreach { newSet => peerSet = newSet }
    }
  }
  
  def mergeLocalAndRemote(peer: Peer, local: Set[Peer], remote: Set[Peer]): Set[Peer] = {
    val (available, unavailable) = (local ++ remote + peer)
      .groupBy { peer => peer.ipAddress }
      .map { case (_, peerSet) => peerSet.maxBy { peer => peer.lastSeen } }.toSet
      .partition { peer => peer.available }
    if (available.size > MAX_SIZE) {
      selectRandomN(available, MAX_SIZE)
    } else if (available.size + unavailable.size > MAX_SIZE) {
      available ++ selectRandomN(unavailable, MAX_SIZE - available.size)
    } else {
      available ++ unavailable
    }
  }
  
  def selectRandomN(peerSet: Set[Peer], n: Int): Set[Peer] = {
    Random.shuffle(peerSet.toList).take(n).toSet
  }
    
  def downloadRemotePeerSet(peer: Peer, unmarshaller: PeerSetUnmarshaller): Option[Set[Peer]] = {
    get(peer.ipAddress, "/peerlist")(unmarshaller)
  }
}