package org.scardiecat.styx.pingpong.service

import akka.actor.ActorRef
import akka.util.Timeout
import org.scardiecat.styx.microservice.marshalling.CommonMarshallers
import org.scardiecat.styx.pingpong.api.{Pong, Ping}
import org.scardiecat.styx.pingpong.marshalling.PingPongMarshallers
import spray.routing.Directives

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._

private [pingpong] object PingPongService extends Directives with PingPongMarshallers with CommonMarshallers {
  import akka.pattern.ask

  implicit val timeout = Timeout(5 seconds)

  def pingPongRoute(pingPongActor : ActorRef) (implicit ec: ExecutionContext) =
    path("ping") {
      get {
        complete{
          (pingPongActor ? Ping("ping")).map( _ match {
            case message:Pong => message
          })
        }
      }
    }

}
