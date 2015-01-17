package com.turbosandwich.persistence

import java.nio.file.Paths
import java.io._

trait Persistent[T] {
  final val LOCAL_STORAGE_DIR = ".sandwich"
  val fileName: String
  
  def marshal(output: OutputStream)(value: T): Unit
  
  def unmarshal(input: InputStream): T
  
  def persistenceFile: File = Paths.get(LOCAL_STORAGE_DIR, fileName).toFile()
  
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