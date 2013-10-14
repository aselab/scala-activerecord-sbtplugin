package com.github.aselab.activerecord.play

import com.github.aselab.activerecord.{Plugin => BasePlugin, _}

object Plugin extends sbt.Plugin {
  val scalaActiverecordSettings = BasePlugin.activerecordSettings

  Generator.register(new ControllerGenerator)
  Generator.register(new RoutesGenerator)
}

