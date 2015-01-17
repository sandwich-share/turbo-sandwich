package com.turbosandwich.persistence

import com.turbosandwich.client.Peer
import com.turbosandwich.client.Peer.PeerSetMarshaller
import com.turbosandwich.client.Peer.PeerSetUnmarshaller
import java.io.Writer
import java.io.Reader

class PersistentPeerSet(
    private val unmarshaller: PeerSetUnmarshaller,
    private val marshaller: PeerSetMarshaller) extends Persistent[Set[Peer]] {
  
  override val fileName = "peer_set"
  
  override def marshal(writer: Writer)(peerSet: Set[Peer]): Unit = marshaller(writer)(peerSet)
  
  override def unmarshal(reader: Reader) = unmarshaller(reader)
}