package com.turbosandwich.persistence

import java.nio.file.Paths
import java.io._

abstract class Persistent[T] {
  final val LOCAL_STORAGE_DIR = ".sandwich"
  val serializationKey: String
  private var _value: Option[T] = None
  PersistenceManager.register(serializationKey, this)
  
  def value: T = _value.get
  
  def value_=(value: T): Unit = {
    _value = Some(value)
  }
  
  def marshal(output: OutputStream)(value: T): Unit
  
  def unmarshal(input: InputStream): T
  
  def persistenceFile: File = Paths.get(LOCAL_STORAGE_DIR, serializationKey).toFile()
  
  def Read(): Unit = {
    val fileInputStream = new FileInputStream(persistenceFile)
    _value = Some(unmarshal(fileInputStream))
    fileInputStream.close()
  }
  
  def Write(): Unit = {
    val fileOutputStream = new FileOutputStream(persistenceFile)
    marshal(fileOutputStream)(_value.get)
    fileOutputStream.close()
  }
}