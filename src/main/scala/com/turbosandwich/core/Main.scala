package com.turbosandwich.core

import com.turbosandwich.persistence.PersistenceManager

object Main {
  
  def preshutdown(): Unit = {
    PersistenceManager.serialize()
  }
  
  // This should possibly be called before some configuration.
  def addShutdownHook(): Unit = {
    Runtime.getRuntime.addShutdownHook(new Thread {
      override def run() {
        preshutdown()
      }
    })
  }
  
  def prestartup(): Unit = {
    PersistenceManager.deserialize()
  }
  
  def main() {
    prestartup()
    addShutdownHook()
    val core = Core.build()
    core.init()
    core.run() // Blocks until everything is done.
  }

}