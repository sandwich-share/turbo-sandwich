package com.turbosandwich.persistence

import java.io.Writer
import java.io.Reader
import java.io.InputStream
import java.io.OutputStream

class PersistentSettings extends Persistent[Settings] {
  override val serializationKey: String = "settings"
  
  override def marshal(writer: OutputStream)(value: Settings): Unit = Settings.marshall(writer)(value)
  
  override def unmarshal(reader: InputStream): Settings = Settings.unmarshall(reader)
}

object PersistentSettings {
  val settings = new PersistentSettings()
}