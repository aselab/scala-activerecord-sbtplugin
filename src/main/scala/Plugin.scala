package com.github.aselab.activerecord

import sbt._
import Keys._

object Plugin extends sbt.Plugin {
  import PluginKeys._

  val activerecordSettings: Seq[sbt.Project.Setting[_]] = Seq(
    generate <<= Task.generate,
    copyTemplates <<= Task.copyTemplates,
    templateDirectory := "project/templates"
  )
}

