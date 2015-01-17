package com.turbosandwich

import java.io.Closeable

package object util {
  
  def using[T, C <: Closeable](any: C)(action: C => T): Option[T] = try {
    Some(action(any))
  } catch {
    case error: Throwable =>
      // TODO(Brendan): Log the error.
      None
  } finally {
    any.close()
  }

}