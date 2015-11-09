package main


object MonolidBoot extends App {

   val frontend = FrontendBoot.main(Array[String]())
   val backend = PingPongBackendApp.main(Array[String]())
}