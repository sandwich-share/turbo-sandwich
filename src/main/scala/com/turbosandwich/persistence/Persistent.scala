package com.turbosandwich.persistence

import java.nio.file.Paths
import java.io.File
import java.io.Writer
import java.io.Reader
import java.io.FileWriter
import java.io.FileReader

trait Persistent[T] {
  final val LOCAL_STORAGE_DIR = ".sandwich"
  val fileName: String
  
  def marshal(writer: Writer)(value: T): Unit
  
  def unmarshal(reader: Reader): T
  
  def persistenceFile: File = Paths.get(LOCAL_STORAGE_DIR, fileName).toFile()
  
  def Read(): T = {
    val fileReader = new FileReader(persistenceFile)
    val result = unmarshal(fileReader)
    fileReader.close()
    result
  }
  
  def Write(value: T): Unit = {
    val fileWriter = new FileWriter(persistenceFile)
    marshal(fileWriter)(value)
    fileWriter.close()
  }
}