import sbt._
import  Keys._

object ApplicationBuild extends Build {

  val appName = "reactive-jmx-console"
  val appVersion = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    "org.webjars" % "angularjs" % "1.1.5-1",
    "org.webjars" % "requirejs" % "2.1.8",
    "org.webjars" % "bootstrap" % "3.0.0"  ,
    "org.webjars" %% "webjars-play" % "2.2.0",
    "fr.janalyse" %% "janalyse-jmx" % "0.6.3" % "compile"
  )

  val main = play.Project(appName, appVersion, appDependencies).settings(
    resolvers += "JAnalyse Repository" at "http://www.janalyse.fr/repository/"

  )
}
