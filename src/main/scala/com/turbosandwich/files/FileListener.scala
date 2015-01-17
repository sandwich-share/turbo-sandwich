package com.turbosandwich.files

import java.nio.file.Path
import java.nio.file.FileSystems
import java.nio.file.StandardWatchEventKinds.{
  ENTRY_CREATE,
  ENTRY_DELETE,
  ENTRY_MODIFY}

class FileListener(val rootDirectory: Path) {
  def listen: Unit = {
    val service = FileSystems.getDefault.newWatchService()
    val key = rootDirectory.register(service, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY)
  }
}