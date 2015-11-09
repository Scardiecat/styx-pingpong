package main

import akka.actor.ActorSystem
import com.typesafe.config.{Config, ConfigFactory}
import org.scardiecat.styx.microservice.Microservice
import org.scardiecat.styx.pingpong.service.PingPongServiceBoot
import spray.routing.Route

import scala.concurrent.ExecutionContext


object FrontendBoot extends App with Microservice{

    val port: String = if (args.length > 0) args(0) else "2553"
    val role: String = if (args.length > 1) args(1) else "frontend"
    lazy val config: Config = {
      ConfigFactory.parseString("akka.remote.netty.tcp.port=" + port)
        .withFallback(ConfigFactory.parseString("akka.cluster.roles = [" + role + "]"))
        .withFallback(ConfigFactory.load("cluster"))
    }

    lazy val actorSystemName: String = "SampleActorSystem"
    actorSystemStartUp(port.toInt, 10000.toInt + port.toInt)


   def  getMicroserviceRoutes(implicit  system:ActorSystem, ec: ExecutionContext): Seq[Route] = {
      val pingPongService = PingPongServiceBoot.boot
      Seq(pingPongService.route)
    }


}