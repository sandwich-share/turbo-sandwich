package com.turbosandwich.persistence

import scala.collection.mutable.Map

object PersistenceManager {
  private val serializerMap = Map[String, Persistent[_]]()
  
  def register(key: String, serializer: Persistent[_]): Unit = {
    serializerMap(key) = serializer
  }
  
  def serialize(): Unit = {
    serializerMap.values.foreach { persistent => persistent.Write() }
  }
  
  def deserialize(): Unit = {
    serializerMap.values.foreach { persistent => persistent.Read() }
  }
}