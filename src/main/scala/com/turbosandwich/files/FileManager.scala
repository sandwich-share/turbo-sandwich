package com.turbosandwich.files

import java.nio.file.Path
import java.io.FileInputStream
import java.io.OutputStream
import com.turbosandwich.util.ChunkyWriter

class FileManager(val sandwichDirectory: Path) {
  private var index = Map[String, Path]()
  private val indexer = new Indexer(sandwichDirectory)
  private val queryEngine = new QueryEngine()
  
  def buildIndex: Unit = {
    index = indexer.walk.map { fileEntry => (fileEntry.fileName, fileEntry.path) }.toMap
  }
  
  def query(query: AbstractQuery): Set[FileEntry] = {
    queryEngine.handleQuery(index)(query)
  }
  
  def fetchFile(path: Path, outputStream: OutputStream): Unit = {
    val file = path.toFile()
    val size = file.length()
    val inputStream = new FileInputStream(file)
    
    val chunkyWriter = new ChunkyWriter(outputStream)
    chunkyWriter.write(inputStream, size)
    inputStream.close()
    chunkyWriter.close()
  }
}