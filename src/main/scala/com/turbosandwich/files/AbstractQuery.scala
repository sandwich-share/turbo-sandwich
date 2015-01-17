package com.turbosandwich.files

sealed abstract class AbstractQuery

case class SimpleQuery(query: String) extends AbstractQuery