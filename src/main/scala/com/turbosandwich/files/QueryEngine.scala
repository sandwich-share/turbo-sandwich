package com.turbosandwich.files

import java.nio.file.Path

class QueryEngine {
  
  def handleQuery(index: Map[String, Path]): PartialFunction[AbstractQuery, Set[FileEntry]] = {
    case SimpleQuery(query: String) => index
      .filter { case (name, path) => name.contains(query) }
      .map { case (name, path) => FileEntry(name, path) }.toSet
      
    case _ => Set[FileEntry]()
  }

}