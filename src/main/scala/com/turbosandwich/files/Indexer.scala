package com.turbosandwich.files

import java.nio.file.Path
import java.io.File

class Indexer(val rootDirectory: Path) {
  def walk: Set[FileEntry] = {
    walk(rootDirectory.toFile())
      .map { file => FileEntry(file.getName, file.toPath().relativize(rootDirectory)) }
  }
  
  private def walk(parent: File): Set[File] = {
    val (directories, files) = parent.listFiles().partition { file => file.isDirectory() }
    directories.flatMap { directory => walk(directory) }.toSet ++ files
  }
}