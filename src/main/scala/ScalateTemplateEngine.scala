package com.github.aselab.activerecord

import sbt._
import org.fusesource.scalate._

class ScalateTemplateEngine(libraryJar: File, baseTemplateDir: File) {

  lazy val engine = {
    val engine = new TemplateEngine
    engine.combinedClassPath = true
    engine.classpath = libraryJar.getAbsolutePath
    engine
  }

  def render(targetFile: String, attributes: Map[String, Any]) = {
    val file = baseTemplateDir / targetFile
    val source =
      if (file.exists)
        TemplateSource.fromFile(file, file.getName)
      else
        TemplateSource.fromURL(getClass.getResource("/" + targetFile))
    engine.layout(source, attributes)
  }
}

