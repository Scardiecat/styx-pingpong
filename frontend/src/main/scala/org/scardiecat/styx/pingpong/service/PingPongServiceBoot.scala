package org.scardiecat.styx.pingpong.service

import akka.actor.{ActorRef, ActorSystem}

import scala.concurrent.ExecutionContext

case class PingPongServiceBoot private(frontendActor: ActorRef){
  def route(implicit ec: ExecutionContext) = PingPongService.pingPongRoute(frontendActor) (ec)
}

object PingPongServiceBoot {

  def boot(implicit system: ActorSystem): PingPongServiceBoot = {

    val frontendActor = system.actorOf(FrontendActor.props, "FrontendActor")

    PingPongServiceBoot(frontendActor)
  }

}