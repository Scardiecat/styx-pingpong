import sbt._

object Dependencies {

  object Version {
    val akka = "2.4.1"
    val spray = "1.3.3"
    val kamon = "0.5.2"
  }

  lazy val api = apiDep
  lazy val frontend = common ++ http ++ metrics
  lazy val backend = common ++ metrics ++ tests

  lazy val pingpong = common

  val common = Seq(
    "ch.qos.logback" % "logback-classic" % "1.1.3",
    "com.typesafe.akka" %% "akka-actor" % Version.akka,
    "com.typesafe.akka" %% "akka-cluster" % Version.akka,
    "com.typesafe.akka" %% "akka-cluster-metrics" % Version.akka,
    "com.typesafe.akka" %% "akka-slf4j" % Version.akka,
    "org.scardiecat" %% "styx-akka-guice" % "0.0.2",
    "org.scardiecat" %% "styx-utils" % "0.0.4",
    "org.scardiecat" %% "styx-akka-seed-node" % "0.0.3"
  )
  
  val tests = Seq(
    "com.typesafe.akka" %% "akka-testkit" % Version.akka % "test",
    "junit" % "junit" % "4.12"
  )

  val metrics = Seq(
    "io.kamon" % "sigar-loader" % "1.6.6-rev002",
    "io.kamon" %% "kamon-statsd" % Version.kamon,
    "io.kamon" %% "kamon-scala" % Version.kamon,
    "io.kamon" %% "kamon-akka" % Version.kamon,
    "io.kamon" %% "kamon-akka-remote" % Version.kamon,
    "io.kamon" %% "kamon-log-reporter" % Version.kamon,
    "io.kamon" %% "kamon-system-metrics" % Version.kamon,
    "org.aspectj" % "aspectjweaver" % "1.8.1"
  )

  val http = Seq(
    "io.spray" %% "spray-can" % Version.spray,
    "io.spray" %% "spray-routing" % Version.spray,
    "io.spray" %%  "spray-json" % "1.3.2",
    "org.json4s" %% "json4s-native" % "3.3.0",
    "org.scalaz" %% "scalaz-core" % "7.1.5",
    "org.scardiecat" %% "styx-microservice" % "0.0.3"
  )

  val apiDep = Seq(
    "com.google.protobuf" % "protobuf-java" % "2.6.1"
  )
}
