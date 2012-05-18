package com.github.aselab.activerecord

import sbt._

object PluginKeys {
  lazy val generate = InputKey[Unit]("generate")
  lazy val copyTemplates = InputKey[Unit]("copy-templates")
  lazy val templateDirectory = SettingKey[String]("template-directory")
}

