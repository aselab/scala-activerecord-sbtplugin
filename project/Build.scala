import sbt._
import Keys._

object Build extends Build {
  lazy val main = Project("main", file("."), settings = mainSettings)

  lazy val mainDependencies = Seq(
    "org.fusesource.scalate" % "scalate-core" % "1.5.3",
    "org.specs2" %% "specs2" % "1.10" % "test",
    "org.slf4j" % "slf4j-nop" % "1.6.4"
  )

  lazy val mainSettings: Seq[Project.Setting[_]] =
    Defaults.defaultSettings ++ ScriptedPlugin.scriptedSettings ++ Seq(
      libraryDependencies ++= mainDependencies,
      sbtPlugin := true,
      name := "scala-activerecord-sbtplugin",
      organization := "com.github.aselab",
      version := "0.1-SNAPSHOT",
      scalacOptions := Seq("-deprecation", "-unchecked"),
      ScriptedPlugin.scriptedBufferLog := false,
      watchSources <++= (ScriptedPlugin.sbtTestDirectory).map{ dir => (dir ***).get }
    )
}

