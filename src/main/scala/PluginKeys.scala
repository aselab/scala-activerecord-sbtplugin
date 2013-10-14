package com.github.aselab.activerecord

import sbt._

object PluginKeys {
  lazy val generate = InputKey[Unit]("generate")
  lazy val copyTemplates = TaskKey[Unit]("copyTemplates")
  lazy val templateDirectory = SettingKey[String]("templateDirectory")
}

