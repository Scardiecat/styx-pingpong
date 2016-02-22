package org.scardiecat.styx.pingpong.service;

import akka.actor.{ActorRef, ActorSystem}
import akka.routing.{Broadcast, FromConfig}
import akka.util.Timeout
import akka.pattern.ask
import org.scardiecat.styx.pingpong.api.{ChangePongMessage, Ping, Definitions}

import scala.concurrent.Future

class PingPongRouteHelper (system:ActorSystem)(implicit timeout:Timeout){
  val callDispatcher: ActorRef = system.actorOf(FromConfig.getInstance.props, "PingPongDispatcher")

  def ping(msg:Ping): Future[Any] =
    this.callDispatcher ? msg
}
