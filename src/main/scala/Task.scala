package com.github.aselab.activerecord

import sbt._
import Keys._
import Project._

object Task {
  import PluginKeys._

  val copyTemplates: Initialize[sbt.InputTask[Unit]] = inputTask {
    (_, PluginKeys.templateDirectory, streams) map {
      case (args, templateDirectory, streams) =>
        val url = getClass.getResource("/templates")
        val logger = streams.log
        IOUtil.copyJarResourses(url, templateDirectory, logger)
    }
  }

  val generate: Initialize[sbt.InputTask[Unit]] = inputTask {
    (_, scalaSource in Compile, state, baseDirectory, templateDirectory) map {
      case (args, sourceDir, s, b, t) =>
        args.toList match {
          case List(generateType, name, fields @ _*) =>
            val libraryJar = s.configuration.provider.scalaProvider.libraryJar
            val baseTemplateDir = IOUtil.baseTemplateDir(b, t)
            val templateEngine =
              new ScalateTemplateEngine(libraryJar, baseTemplateDir)
            implicit val info =
              GenerateInfo(templateEngine, sourceDir, name, fields)

            val generator = generateType match {
              case "model" => new ModelGenerator
              case "scaffold" => new ScaffoldGenerator
              case _ => throw new Exception("error")
            }
            generator.generate
          case _ =>
            println("Usage: generate [Generator] [Name] [field[:type] field[:type]]")
        }
    }
  }
}

