import sbt._
import Keys._

import com.github.aselab.activerecord.Plugin._

object MyBuild extends Build {
  lazy val root = Project("root", file("."),
    settings = Defaults.defaultSettings ++ activerecordSettings)
}
