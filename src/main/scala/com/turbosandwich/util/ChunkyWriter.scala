package com.turbosandwich.util

import java.io.InputStream
import java.io.OutputStream
import java.io.Closeable

// TODO: We might want to choose a better default chunkSize.
class ChunkyWriter(private val writer: OutputStream, val chunkSize: Int = 64 * 1024) extends Closeable {

  def write(reader: InputStream, totalAmountToRead: Long) {
    var amountLeftToRead = totalAmountToRead
    val chunk = new Array[Byte](chunkSize)
    do {
      var amountToWrite = reader.read(chunk)
      writer.write(chunk, 0, amountToWrite)
      amountLeftToRead -= amountToWrite
    } while (amountLeftToRead != 0)
  }
  
  override def close() {
    writer.close()
  }
}