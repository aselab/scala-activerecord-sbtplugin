package com.github.aselab.activerecord.scalatra

import com.github.aselab.activerecord.{Plugin => BasePlugin, _}

object Plugin extends sbt.Plugin {
  val scalatraActiverecordSettings = BasePlugin.activerecordSettings

  Generator.register(new ControllerGenerator)
}

