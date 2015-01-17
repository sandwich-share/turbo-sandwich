package com.turbosandwich.files

import java.nio.file.Path
import java.io.InputStream
import java.io.OutputStream

case class FileEntry(fileName: String, path: Path)

object FileEntry {
  def marshal(outputStream: OutputStream)(fileEntries: Set[FileEntry]): Unit = ???
  
  def unmarshal(inputStream: InputStream): Set[FileEntry] = ???
}