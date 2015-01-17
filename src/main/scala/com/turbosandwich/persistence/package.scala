package com.turbosandwich

import java.io.Writer
import java.io.Reader

package object persistence {
  type Marshaller[T] = Writer => T => Unit
  type Unmarshaller[T] = Reader => T
}