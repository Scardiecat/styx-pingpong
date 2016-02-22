package main

import org.scardiecat.styx.pingpong.api.Definitions
import org.scardiecat.styx.seednode.main.SeedNodeBoot


object MonolithBoot extends App {
   val seed1 = SeedNodeBoot.main(Array[String]("-p","2551","-n", Definitions.ActorSystemName,"--seeds", java.net.InetAddress.getLocalHost.getHostAddress +":2551"))
   val frontend = PingPongFrontendBoot.main(Array[String]("-p","2553","-n", Definitions.ActorSystemName,"--seeds", java.net.InetAddress.getLocalHost.getHostAddress +":2551"))
   val backend = PingPongBackendBoot.main(Array[String]("-p","2554","-n", Definitions.ActorSystemName,"--seeds", java.net.InetAddress.getLocalHost.getHostAddress +":2551"))
}