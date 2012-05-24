package com.github.aselab.activerecord.scalatra

import com.github.aselab.activerecord.{Plugin => BasePlugin, _}

object Plugin extends sbt.Plugin {
  val scalatraActiverecordSettings: Seq[sbt.Project.Setting[_]] =
    BasePlugin.activerecordSettings

  Generator.register("controller", new ControllerGenerator, Parser.controllerParser)
}

