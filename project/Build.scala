import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "dimlib"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    javaCore,
    javaJdbc,
    javaEbean,
    "securesocial" %% "securesocial" % "master-SNAPSHOT",
    "com.github.julienrf" %% "jsmessages" % "1.4.2" from "http://julienrf.github.io/repo/com/github/julienrf/play-jsmessages_2.10/1.4.2/play-jsmessages_2.10-1.4.2.jar"
  )

  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here 
	resolvers += Resolver.url("sbt-plugin-snapshots", new URL("http://repo.scala-sbt.org/scalasbt/sbt-plugin-snapshots/"))(Resolver.ivyStylePatterns)
  )
}
