package com.turbosandwich

import java.net.InetAddress
import java.nio.file.{
  Path,
  Paths}
import com.turbosandwich.net._
import com.turbosandwich.util._
import java.io.FileOutputStream
import com.turbosandwich.persistence.PersistentSettings
import java.io.File
import java.net.HttpURLConnection
import java.net.URI
import java.io.OutputStream
import java.io.InputStream
import com.turbosandwich.files.FileEntry

package object client {

  def getFile(address: InetAddress, path: Path): Unit = {
    val localPath = Paths.get(PersistentSettings.settings.value + File.separator + path)
    onSuccess(new URI("http", null, address.getHostAddress, portHash(address), "/files/" + path, null, null).toURL).foreach { url =>
      val connection = url.openConnection.asInstanceOf[HttpURLConnection]
      val file = localPath.toFile
      onSuccess(file.getParentFile).flatMap(notNull(_)).flatMap(file => onSuccess(() => file.mkdirs()))
      onSuccess(() => file.createNewFile())
      using(connection.getInputStream, new FileOutputStream(file)) { (connectionReader: InputStream, fileWriter: OutputStream) =>
        new ChunkyWriter(fileWriter).write(connectionReader, connection.getContentLengthLong)
      }
    }
  }
  
  def getQuery(address: InetAddress, path: Path): Option[Set[FileEntry]] = ???
}