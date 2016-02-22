package org.scardiecat.styx.pingpong.service

import akka.actor.{ActorRef, ActorSystem}
import akka.pattern.ask
import akka.routing.{Broadcast, FromConfig}
import akka.util.Timeout
import org.scardiecat.styx.pingpong.api.{ChangePongMessage, Ping}

import scala.concurrent.Future

class AdminRouteHelper(system:ActorSystem)(implicit timeout:Timeout){
  val callDispatcher: ActorRef = system.actorOf(FromConfig.getInstance.props, "AdminDispatcher")

  def changePong(msg:ChangePongMessage): Unit =
    this.callDispatcher ! Broadcast(msg)
}
