package com.github.aselab.activerecord

import sbt._
import Keys._
import Project._
import collection.JavaConversions.enumerationAsScalaIterator

object Task {
  import PluginKeys._
  import IOUtil._

  def copyTemplates = Def.task {
    val loader = this.getClass.getClassLoader
    val dir = templateDirectory.value
    val logger = streams.value.log
    IOUtil.copyResources(loader, "templates", dir, logger)
  }

  def generate = Def.inputTask { Generator.allParser.parsed match {
    case (name: String, args) =>
      val sourceDir = (scalaSource in Compile).value
      val templateDir = projectFile(templateDirectory.value,baseDirectory.value)
      implicit val logger = streams.value.log
      val scalaJar = scalaInstance.value.libraryJar
      val templateEngine = new ScalateTemplateEngine(scalaJar, templateDir)
      val info = GenerateInfo(templateEngine, sourceDir, args)
      Generator.generators(name).generate(info)
  }}

  def projectFile(path: String, base: File) = {
    if (path.startsWith("/")) file(path) else base / path
  }
}

