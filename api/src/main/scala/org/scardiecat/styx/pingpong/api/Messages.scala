package org.scardiecat.styx.pingpong.api

sealed trait RequestMessage

case class  Ping(txt:String) extends  RequestMessage


sealed trait ResponseMessage

case class Pong(txt:String) extends ResponseMessage

sealed trait AdminMessage

case class ChangePongMessage(txt:String) extends AdminMessage

sealed trait ErrorMessage

case class GenericError(httpErrorCode: Int, message: String) extends  ErrorMessage
