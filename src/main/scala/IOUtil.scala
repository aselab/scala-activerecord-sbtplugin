package com.github.aselab.activerecord

import sbt._

object IOUtil {
  def baseTemplateDir(baseDirectory: File, templateDir: String): File =
    if (templateDir.startsWith("/"))
      file(templateDir)
    else
      baseDirectory / templateDir
}

