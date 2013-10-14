package com.github.aselab.activerecord

import sbt._
import Keys._

object Plugin extends sbt.Plugin {
  import PluginKeys._

  val activerecordSettings = Seq(
    generate <<= Task.generate,
    copyTemplates <<= Task.copyTemplates,
    templateDirectory := (baseDirectory.value / "templates").getPath
  )
}

