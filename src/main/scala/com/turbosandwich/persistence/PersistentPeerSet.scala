package com.turbosandwich.persistence

import com.turbosandwich.client.Peer
import com.turbosandwich.client.Peer.PeerSetMarshaller
import com.turbosandwich.client.Peer.PeerSetUnmarshaller
import java.io.{OutputStream, InputStream, Writer, Reader}

class PersistentPeerSet(
    private val unmarshaller: PeerSetUnmarshaller,
    private val marshaller: PeerSetMarshaller) extends Persistent[Set[Peer]] {
  
  override val serializationKey = "peer_set"
  
  override def marshal(output: OutputStream)(peerSet: Set[Peer]): Unit = marshaller(output)(peerSet)
  
  override def unmarshal(input: InputStream) = unmarshaller(input)
}