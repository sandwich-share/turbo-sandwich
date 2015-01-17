package com.turbosandwich.persistence

import java.io.Writer
import java.io.Reader

class PersistentSettings extends Persistent[Settings] {
  override val serializationKey: String = "settings"
  
  override def marshal(writer: Writer)(value: Settings): Unit = Settings.marshall(writer)(value)
  
  override def unmarshal(reader: Reader): Settings = Settings.unmarshall(reader)
}