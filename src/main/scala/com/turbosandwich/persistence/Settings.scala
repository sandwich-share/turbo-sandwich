package com.turbosandwich.persistence

import java.io.InputStream
import java.io.OutputStream
import java.nio.file.Path

case class Settings(sandwichDirectory: Path)

object Settings {
  def marshall(writer: OutputStream)(settings: Settings): Unit = ???
  
  def unmarshall(reader: InputStream): Settings = ???
}