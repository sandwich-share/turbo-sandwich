package com.turbosandwich.client

import scala.collection.mutable
import scala.collection.mutable.Map
import com.turbosandwich.files.AbstractQuery
import com.turbosandwich.files.FileEntry
import com.turbosandwich.files.QueryResponse
import java.net.InetAddress
import com.turbosandwich.files.FullQuery
import com.turbosandwich.files.QueryWithSource
import com.turbosandwich.net.computeLocalIPAddress

// TODO: Implement caching.
// TODO: Implement mapping from user input to query types.
class QueryEngine {
  final val STARTING_TTL = 3
  val cache = Map[AbstractQuery, Set[QueryResponse]]()
  val seenQueries = mutable.Set[QueryWithSource]()

  def query(query: AbstractQuery, peerSet: Set[Peer]): Set[QueryResponse] = {
    val address = computeLocalIPAddress()
    broadcast(FullQuery(query, address, STARTING_TTL), peerSet)
  }
  
  def handleQuery(query: FullQuery, peerSet: Set[Peer]): Option[Set[QueryResponse]] = {
    if (seenQueries(query.query)) {
      None
    } else {
      seenQueries += query.query
      Some(broadcast(query.forward(), peerSet.filterNot { peer => peer.ipAddress == query.query.sourceAddress }))
    }
  }
  
  def broadcast(query: FullQuery, peerSet: Set[Peer]): Set[QueryResponse] = {
    val responses = peerSet.flatMap { peer => sendQuery(query, peer.ipAddress) }
    cache(query.query.query) = responses
    responses
  }
  
  def sendQuery(query: FullQuery, address: InetAddress): Set[QueryResponse] = ???
  
}