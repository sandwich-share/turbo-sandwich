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
  
  def using[T, C](any: C)(lastAction: C => Unit)(action: C => T): Option[T] = try {
    Some(action(any))
  } catch {
    case error: Throwable =>
      // log.error("action failed", error)
      None
  } finally {
    lastAction(any)
  }

  def using[T, C1 <: Closeable, C2 <: Closeable](any1: C1, any2: C2)(action: (C1, C2) => T): Option[T] = try {
    Some(action(any1, any2))
  } catch {
    case error: Throwable =>
      // log.error("action failed", error)
      None
  } finally {
    any1.close()
    any2.close()
  }

  def onSuccess[T](action: () => T): Option[T] = try {
    Some(action())
  } catch {
    case error: Throwable =>
      // log.error("action failed", error)
      None
  }

  def notNull[T](action: () => T): Option[T] = {
    val result = action()
    if (result != null) {
      Some(result)
    } else {
      None
    }
  }

  def notNull[T](value: T): Option[T] = if (value != null) Some(value) else None

}