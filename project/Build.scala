import sbt._
import play.Project._

object ApplicationBuild extends Build {

  val appName = "reactive-jmx-console"
  val appVersion = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    "org.webjars" % "angularjs" % "1.1.5-1",
    "org.webjars" % "requirejs" % "2.1.8",
    "org.webjars" % "bootstrap" % "3.0.0"  ,
    "org.webjars" %% "webjars-play" % "2.1.0-3"
  )

  val main = play.Project(appName, appVersion, appDependencies)
}
