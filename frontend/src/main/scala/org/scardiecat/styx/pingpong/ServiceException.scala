package org.scardiecat.styx.pingpong

sealed trait ServiceException { _: RuntimeException => def errorCode: Int }

case class GenericException (errorCode: Int, msg: String, cause: Exception = null) extends RuntimeException(msg, cause) with ServiceException
