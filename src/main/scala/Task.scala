package com.github.aselab.activerecord

import sbt._
import Keys._
import Project._

object Task {
  import PluginKeys._

  val copyTemplates: Initialize[sbt.InputTask[Unit]] = inputTask {
    (_, PluginKeys.templateDirectory, streams) map {
      case (args, templateDirectory, streams) =>
        import IOUtil._
        val urls = this.getClass.getClassLoader.getResources("templates")
        val logger = streams.log
        urls.foreach(IOUtil.copyJarResourses(_, templateDirectory, logger))
    }
  }

  Generator.register("model", new ModelGenerator, Parser.modelParser)

  val generate: Initialize[sbt.InputTask[Unit]] = InputTask(_ => Generator.allParser) {
    (_, scalaSource in Compile, streams, state, baseDirectory, templateDirectory) map {
      case ((generateType: String, parsed), sourceDir, streams, s, b, t) =>
        implicit val logger = streams.log
        val libraryJar = s.configuration.provider.scalaProvider.libraryJar
        val baseTemplateDir = IOUtil.baseTemplateDir(b, t)
        val templateEngine = new ScalateTemplateEngine(libraryJar, baseTemplateDir)
        val info = GenerateInfo(templateEngine, sourceDir, parsed)
        Generator.generators(generateType).generate(info)
    }
  }
}

