package com.turbosandwich.core

import com.turbosandwich.files.AbstractQuery
import com.turbosandwich.files.FileEntry
import java.net.InetAddress

class Controller {
  
  def shutdown(): Unit = ???
  
  def query(query: AbstractQuery): Set[FileEntry] = ???
  
  def downloadFile(ipAddress: InetAddress, fileEntry: FileEntry): Unit = ???
  
  def downloadPeerSet(ipAddress: InetAddress): Unit = ???
  
}