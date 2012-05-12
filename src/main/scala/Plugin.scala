package com.github.aselab.activerecord

import sbt._
import Keys._
import Project._
import IO._

object Plugin extends sbt.Plugin {
  val sbtPluginSettings: Seq[sbt.Project.Setting[_]] = Seq(
    PluginKeys.generate <<= Task.generate
  )
}

object Task {
  val generate: Initialize[sbt.InputTask[Unit]] = inputTask { (task: TaskKey[Seq[String]]) =>
    (task, scalaSource in Compile) map { case (args, sourceDirectory) =>
      args.toList match {
        case List(generator, name, fields @ _*) =>
          // TODO: Implementation here

          println(sourceDirectory)
          println(generator, name, fields)
          println(templates.html.hoge(name, fields))
          println(templates.txt.fuga(name, fields))

          // model generation from template sample...
          val fileName = name.capitalize + ".scala"
          val dir = sourceDirectory / "main" / "models"
          val modelFile = dir / fileName
          val contents = templates.txt.fuga(name, fields).toString
          IO.createDirectory(dir)
          if (!modelFile.exists) {
            IO.write(modelFile, contents)
            println("created: " + modelFile)
          } else {
            println("exists: " + modelFile)
          }
        case _ =>
          println("Usage: generate [Generator] [Name] [field[:type] field[:type]]")
      }
    }
  }
}

object PluginKeys {
  val generate = InputKey[Unit]("generate")
}
