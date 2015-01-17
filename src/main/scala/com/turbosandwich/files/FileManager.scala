package com.turbosandwich.files

import java.nio.file.Path

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
}