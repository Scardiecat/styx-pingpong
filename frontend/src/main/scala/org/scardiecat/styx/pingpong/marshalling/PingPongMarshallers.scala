package org.scardiecat.styx.pingpong.marshalling


import org.scardiecat.styx.microservice.marshalling.CommonProtocolMarshallers
import org.scardiecat.styx.pingpong.api.Pong
import spray.httpx.SprayJsonSupport
import spray.json.DefaultJsonProtocol._
import spray.json.{JsString, JsValue, RootJsonFormat}

trait PingPongMarshallers extends SprayJsonSupport with CommonProtocolMarshallers {
    implicit val pongFormat = jsonFormat1(Pong)
}

