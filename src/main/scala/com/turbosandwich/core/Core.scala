package com.turbosandwich.core

import com.turbosandwich.files.FileManager
import com.turbosandwich.server.ServerImpl
import com.turbosandwich.client.ControlLoop
import com.turbosandwich.persistence.PersistentSettings
import com.turbosandwich.net._

class Core(
    private val fileManager: FileManager,
    private val client: ControlLoop,
    private val server: ServerImpl) {
  
  def init(): Unit = {
  }
  
  def run(): Unit = {
    wait()
  }
}

object Core {
  def build(): Core = {
    val settings = PersistentSettings.settings.value
    val ipAddress = computeLocalIPAddress()
    val port = portHash(ipAddress)
    
    val fileManager = new FileManager(settings.sandwichDirectory)
    new Core(
        new FileManager(settings.sandwichDirectory),
        new ControlLoop,
        new ServerImpl(ipAddress, port, fileManager))
  }
}