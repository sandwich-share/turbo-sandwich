package com.turbosandwich.files

import java.net.InetAddress

case class FullQuery(query: QueryWithSource, ttl: Int) {
  def this(query: AbstractQuery, sourceAddress: InetAddress, ttl: Int) = 
    this(QueryWithSource(query, sourceAddress), ttl)
    
  def forward() = FullQuery(query, ttl - 1)
}

object FullQuery {
  def apply(query: AbstractQuery, sourceAddress: InetAddress, ttl: Int): FullQuery = { 
    FullQuery(QueryWithSource(query, sourceAddress), ttl)
  }
}

case class QueryWithSource(query: AbstractQuery, sourceAddress: InetAddress)

sealed abstract class AbstractQuery

case class SimpleQuery(query: String) extends AbstractQuery