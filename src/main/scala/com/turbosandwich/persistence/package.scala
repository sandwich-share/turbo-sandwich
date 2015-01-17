package com.turbosandwich

import java.io.{OutputStream, InputStream}

package object persistence {
  type Marshaller[T] = OutputStream => T => Unit
  type Unmarshaller[T] = InputStream => T
}