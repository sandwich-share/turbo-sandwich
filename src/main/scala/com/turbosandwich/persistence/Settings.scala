package com.turbosandwich.persistence

import java.io.Writer
import java.io.Reader

case class Settings(sandwichDirectory: String)

object Settings {
  def marshall(writer: Writer)(settings: Settings): Unit = ???
  
  def unmarshall(reader: Reader): Settings = ???
}