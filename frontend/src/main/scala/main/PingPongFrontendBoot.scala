package main

import akka.actor.ActorSystem
import com.typesafe.config.{Config, ConfigFactory}
import kamon.Kamon
import org.scardiecat.styx.pingpong.api.Definitions
import org.scardiecat.styx.DockerAkkaUtils
import org.scardiecat.styx.microservice.Microservice
import org.scardiecat.styx.pingpong.service.PingPongServiceBoot
import org.scardiecat.styx.utils.commandline.CommandlineParser

import spray.routing.Route
import scala.concurrent.ExecutionContext


object PingPongFrontendBoot extends App with Microservice{
  Kamon.start()

  val fallbackConfig:Config = ConfigFactory.load()
  var commandline = CommandlineParser.parse(args,meta.BuildInfo.name+":"+meta.BuildInfo.version, fallbackConfig, Seq[String](Definitions.FrontendRoleName))
  val hostAddress: String = java.net.InetAddress.getLocalHost.getHostAddress
  lazy val actorSystemName:String = commandline.actorSystemName
  val config: Config = DockerAkkaUtils.dockerAkkaConfigProvider(fallbackConfig,hostAddress,commandline)
  actorSystemStartUp(config.getString("akka.remote.netty.tcp.port").toInt, 10000.toInt + commandline.port.toInt)


  def getMicroserviceRoutes(implicit system: ActorSystem, ec: ExecutionContext): Seq[Route] = {
    val pingPongService = PingPongServiceBoot.boot
    Seq(pingPongService.route, pingPongService.adminRoute)
  }



}