import NativePackagerHelper._


val commonSettings = Seq(
  organization := "org.scrdiecat",
  version := "0.0.1-SNAPSHOT",
  scalaVersion := "2.11.7",

  // build info
  buildInfoPackage := "meta",
  buildInfoOptions += BuildInfoOption.ToJson,
  buildInfoKeys := Seq[BuildInfoKey](
    name, version, scalaVersion,
    "sbtNativePackager" -> "1.0.0"
  )
)

lazy val root = (project in file("."))
  .enablePlugins(BuildInfoPlugin, JavaAppPackaging)
  .settings(
    name := """styx-pingpong""",
    publishMavenStyle := false,
    libraryDependencies ++= Dependencies.spray,
    commonSettings
  ).aggregate(api, backend)
//
// API
//
lazy val api = (project in file("api"))
  .enablePlugins(BuildInfoPlugin, JavaAppPackaging)
  .settings(
    name := "styx-pingpong-api",
    commonSettings,
    publishMavenStyle := false
  )

lazy val backend = (project in file("backend"))
  .enablePlugins(BuildInfoPlugin, JavaAppPackaging)
  .settings(
    name := "styx-pingpong-backend",
    libraryDependencies ++= Dependencies.backend,
    javaOptions ++= Seq(
      "-Djava.library.path=" + (baseDirectory.value / "sigar" ).getAbsolutePath,
      "-Xms128m", "-Xmx1024m"),
    // this enables custom javaOptions
    fork in run := false,
    mappings in Universal ++= directory(baseDirectory.value / "sigar"),
    bashScriptExtraDefines ++= Seq(
      """declare -r sigar_dir="$(realpath "${app_home}/../sigar")"""",
      """addJava "-Djava.library.path=${sigar_dir}""""
    ),
    mainClass in (Compile)  := Some("main.PingPongBackendApp"),
    commonSettings
  ).dependsOn(api)