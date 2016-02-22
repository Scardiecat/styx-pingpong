package org.scardiecat.styx.pingpong.service

import akka.actor.{ActorRef, ActorSystem}

import scala.concurrent.ExecutionContext

case class PingPongServiceBoot private(){
  def route(implicit ec: ExecutionContext, system: ActorSystem) = PingPongService.pingPongRoute() (ec, system)

  def adminRoute(implicit ec: ExecutionContext, system: ActorSystem) = PingPongService.adminRoute() (ec, system)
}

object PingPongServiceBoot {

  def boot(implicit system: ActorSystem): PingPongServiceBoot = {
    PingPongServiceBoot()
  }

}