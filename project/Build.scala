import sbt._
import Keys._

object Build extends Build {
  lazy val main = Project("main", file("."), settings = mainSettings)

  lazy val scalatra = Project("scalatra", file("scalatra/"), settings = scalatraSettings) dependsOn(main)

  lazy val play20 = Project("play20", file("play20/"), settings = play20Settings) dependsOn(main)

  lazy val mainDependencies = Seq(
    "org.fusesource.scalate" % "scalate-core" % "1.5.3",
    "org.specs2" %% "specs2" % "1.10" % "test",
    "org.slf4j" % "slf4j-nop" % "1.6.4"
  )

  lazy val defaultSettings =
    Defaults.defaultSettings ++ ScriptedPlugin.scriptedSettings ++ Seq(
      libraryDependencies ++= mainDependencies,
      sbtPlugin := true,
      organization := "com.github.aselab",
      scalacOptions := Seq("-deprecation", "-unchecked"),
      ScriptedPlugin.scriptedBufferLog := false,
      watchSources <++= (ScriptedPlugin.sbtTestDirectory).map{ dir => (dir ***).get }
    )

  lazy val mainSettings: Seq[Project.Setting[_]] = defaultSettings ++ Seq(
    name := "scala-activerecord-sbtplugin",
    version := "0.1-SNAPSHOT"
  )

  lazy val scalatraSettings: Seq[Project.Setting[_]] = defaultSettings ++ Seq(
    name := "scalatra-activerecord-sbtplugin",
    version := "0.1-SNAPSHOT"
  )

  lazy val play20Settings: Seq[Project.Setting[_]] = defaultSettings ++ Seq(
    name := "play20-activerecord-sbtplugin",
    version := "0.1-SNAPSHOT"
  )
}

