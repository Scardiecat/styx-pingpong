enablePlugins(GitVersioning)

val commonSettings = Seq(
  organization := "org.scardiecat",
  git.baseVersion := "0.0.3",
  git.gitTagToVersionNumber := { tag: String =>
    if(tag matches "[0-9]+\\..*") Some(tag)
    else None
  },
  git.useGitDescribe := true,
  scalaVersion := "2.11.7",
  scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature", "-language:existentials", "-language:higherKinds"),


  // build info
  buildInfoPackage := "meta",
  buildInfoOptions += BuildInfoOption.ToJson,
  buildInfoOptions += BuildInfoOption.BuildTime,
  buildInfoKeys := Seq[BuildInfoKey](
    name, version, scalaVersion
  ),
  publishMavenStyle := true,
  bintrayReleaseOnPublish in ThisBuild := true,
  bintrayPackageLabels := Seq("styx", "scala", "Akka"),
  licenses += ("MIT", url("http://opensource.org/licenses/MIT"))
)

val commonDockerSettings = Seq(
  dockerBaseImage := "frolvlad/alpine-oraclejdk8",
  dockerExposedPorts := Seq(2551),
  maintainer in Docker := "Ralf Mueller <docker@scardiecat.org>",
  dockerRepository := Some("magicmoose-docker-registry.bintray.io/scardiecat")
)

lazy val root = (project in file("."))
  .enablePlugins(BuildInfoPlugin, AshScriptPlugin,DockerPlugin, GitVersioning)
  .settings(
    name := """styx-pingpong""",
    commonSettings
  ).aggregate(api, backend, frontend, monolith)
//
// API
//
lazy val api = (project in file("api"))
  .enablePlugins(BuildInfoPlugin, AshScriptPlugin,DockerPlugin, GitVersioning)
  .settings(
    name := "styx-pingpong-api",
    libraryDependencies ++= Dependencies.api,
    commonSettings
  )

lazy val backend = (project in file("backend"))
  .enablePlugins(BuildInfoPlugin, AshScriptPlugin,DockerPlugin, GitVersioning)
  .settings(
    name := "styx-pingpong-backend",
    libraryDependencies ++= Dependencies.backend,
    javaOptions ++= Seq(
      "-Xms128m", "-Xmx1024m"),
    // this enables custom javaOptions
    mainClass in (Compile)  := Some("main.PingPongBackendBoot"),
    commonSettings,
    commonDockerSettings,
    aspectjSettings,
    javaOptions in run <++= AspectjKeys.weaverOptions in Aspectj,
    fork in run :=true,

    mappings in Universal += {
      // we are using the reference.conf as default application.conf
      // the user can override settings here
      val conf = (resourceDirectory in Compile).value / "application.ini"
      conf -> "conf/application.ini"
    }
  ).dependsOn(api)

lazy val frontend = (project in file("frontend"))
  .enablePlugins(BuildInfoPlugin, AshScriptPlugin,DockerPlugin, GitVersioning)
  .settings(
    name := "styx-pingpong-frontend",
    libraryDependencies ++= Dependencies.frontend,
    mainClass in (Compile)  := Some("main.PingPongFrontendBoot"),
    commonSettings,
    dockerExposedPorts ++= Seq(12551),
    commonDockerSettings,
    aspectjSettings,
    javaOptions in run <++= AspectjKeys.weaverOptions in Aspectj,
    fork in run :=true,

    mappings in Universal += {
      // we are using the reference.conf as default application.conf
      // the user can override settings here
      val conf = (resourceDirectory in Compile).value / "application.ini"
      conf -> "conf/application.ini"
    }
  ).dependsOn(api)

lazy val monolith = (project in file("monolith"))
  .enablePlugins(BuildInfoPlugin, AshScriptPlugin,DockerPlugin, GitVersioning)
  .settings(
    name := "styx-pingpong-monolith",
    mainClass in (Compile)  := Some("main.MonolithBoot"),
    commonSettings,
    commonDockerSettings,
    dockerExposedPorts ++= Seq(2552, 2553, 12553)
  ).dependsOn(frontend, backend)