import sbt._

object Dependencies {

  object Version {
    val akka = "2.4.0"
    val spray = "1.3.3"
  }

  object dependantProjects {
    val microservice = "master"
  }

  lazy val microservice = RootProject(uri("https://github.com/Scardiecat/styx-microservice.git#%s".format(dependantProjects.microservice)))

  lazy val spray = common ++ http

  lazy val pingpong = common

  val common = Seq(
    "ch.qos.logback" % "logback-classic" % "1.1.3",
    "com.typesafe.akka" %% "akka-actor" % Version.akka,
    "com.typesafe.akka" %% "akka-cluster" % Version.akka,
    "com.typesafe.akka" %% "akka-slf4j" % Version.akka
  )
  
  val tests = Seq(
    "com.typesafe.akka" %% "akka-testkit" % Version.akka % "test"
  )

  val http = Seq(
    "io.spray" %% "spray-can" % Version.spray,
    "io.spray" %% "spray-routing" % Version.spray,
    "io.spray" %%  "spray-json" % "1.3.2",
    "org.json4s" %% "json4s-native" % "3.3.0",
    "org.scalaz" %% "scalaz-core" % "7.1.5"
  )

}
