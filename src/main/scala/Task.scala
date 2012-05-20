package com.github.aselab.activerecord

import sbt._
import Keys._
import Project._

object Task {
  import PluginKeys._

  implicit def enumToIterator[A](e : java.util.Enumeration[A]) = new Iterator[A] {
    def next = e.nextElement
    def hasNext = e.hasMoreElements
  }

  val copyTemplates: Initialize[sbt.InputTask[Unit]] = inputTask {
    (_, baseDirectory, PluginKeys.templateDirectory) map { case (args, b, t) =>
      getClass.getResource("/templates").openConnection match {
        case connection: java.net.JarURLConnection =>
          val entryName = connection.getEntryName
          val jarFile = connection.getJarFile
          val entries = jarFile.entries.filter(_.getName.startsWith(entryName))
          entries.foreach { e =>
            val fileName = e.getName.drop(entryName.size)
            if (!e.isDirectory)
              IO.transfer(jarFile.getInputStream(e), file(t) / fileName)
            else
              IO.createDirectory(file(t) / fileName)
          }
      }
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

