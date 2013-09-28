import sbt._
import Keys._

object Build extends Build {
  val _version = "0.2.3-SNAPSHOT"

  lazy val main = Project("main", file("."), settings = mainSettings)

  lazy val scalatra = Project("scalatra", file("scalatra/"), settings = scalatraSettings) dependsOn(main)

  lazy val play20 = Project("play20", file("play20/"), settings = play20Settings) dependsOn(main)

  lazy val mainDependencies = Seq(
    "org.fusesource.scalate" %% "scalate-core" % "1.6.1",
    "org.specs2" %% "specs2" % "2.2.2" % "test",
    "org.slf4j" % "slf4j-nop" % "1.7.5"
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

  lazy val mainSettings = defaultSettings ++ Seq(
    name := "scala-activerecord-sbtplugin",
    version := _version
  )

  lazy val scalatraSettings = defaultSettings ++ Seq(
    name := "scalatra-activerecord-sbtplugin",
    version := _version
  )

  lazy val play20Settings = defaultSettings ++ Seq(
    name := "play20-activerecord-sbtplugin",
    version := _version
  )
}

