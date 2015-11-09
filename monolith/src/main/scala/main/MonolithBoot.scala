package main


object MonolithBoot extends App {

   val frontend = FrontendBoot.main(Array[String]())
   val backend = PingPongBackendApp.main(Array[String]())
}