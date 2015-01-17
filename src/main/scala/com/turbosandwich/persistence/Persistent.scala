package com.turbosandwich.persistence

import java.nio.file.Paths
import java.io._

abstract class Persistent[T <: AnyRef] {
  final val LOCAL_STORAGE_DIR = ".sandwich"
  val serializationKey: String
  
  def marshal(output: OutputStream)(value: T): Unit
  
  def unmarshal(input: InputStream): T
  
  def persistenceFile: File = Paths.get(LOCAL_STORAGE_DIR, serializationKey).toFile()
  
  def Read(): T = {
    val fileInputStream = new FileInputStream(persistenceFile)
    val result = unmarshal(fileInputStream)
    fileInputStream.close()
    result
  }
  
  def Write(value: T): Unit = {
    val fileOutputStream = new FileOutputStream(persistenceFile)
    marshal(fileOutputStream)(value)
    fileOutputStream.close()
  }
}