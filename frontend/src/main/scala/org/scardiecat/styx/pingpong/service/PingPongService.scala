package org.scardiecat.styx.pingpong.service

import akka.actor.{ActorSystem, ActorRef}
import akka.routing.Broadcast
import akka.util.Timeout
import org.scardiecat.styx.microservice.marshalling.CommonMarshallers
import org.scardiecat.styx.pingpong.GenericException
import org.scardiecat.styx.pingpong.api.{Definitions, ChangePongMessage, Pong, Ping}
import org.scardiecat.styx.pingpong.marshalling.PingPongMarshallers
import spray.routing.{ExceptionHandler, Directives}
import spray.http.StatusCodes._

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._

private [pingpong] object PingPongService extends Directives with PingPongMarshallers with CommonMarshallers {
  import akka.pattern.ask

  implicit val timeout = Timeout(5 seconds)
  val exceptionHandler = ExceptionHandler {
    case ex: GenericException => complete(ex.errorCode, ex.msg)
  }
  def pingPongRoute() (implicit ec: ExecutionContext, system: ActorSystem) =
    handleExceptions(exceptionHandler) {
      val route = new PingPongRouteHelper(system)
      path("ping") {
        get {
          complete {
            route.ping(Ping("ping")).map(_ match {
              case message: Pong => message
            })
          }
        }
      }
    }

  def adminRoute() (implicit ex:ExecutionContext, system: ActorSystem) =
    handleExceptions(exceptionHandler) {
      val route = new AdminRouteHelper(system)
      path("admin" / "setPong") {
        post {
          respondWithStatus(Created) {
            handleWith { changePong: ChangePongMessage =>
              route.changePong(changePong)
            }
          }
        }
      }
    }
}
