package com.github.aselab.activerecord

import sbt._
import sbt.complete.DefaultParsers._

case class GenerateInfo(
  engine: ScalateTemplateEngine,
  sourceDir: File,
  parsed: Any
)

trait Generator {
  def generate(info: GenerateInfo): Unit
  def help: String
}

object Generator {
  val generators = collection.mutable.Map[String, Generator]()
  val parsers = collection.mutable.MutableList[complete.Parser[_]]()

  lazy val allParser = parsers.reduceLeft {(a, b) => a | b}

  def register(name: String, generator: Generator): Unit =
    register(name, generator, Parser.default(name))

  def register(name: String, generator: Generator, parser: complete.Parser[_]) {
    generators += (name -> generator)
    parsers += parser !!! "Usage: generate %s %s".format(name, generator.help)
  }
}

class ModelGenerator extends Generator {

  def generate(info: GenerateInfo) {
    import info._
    lazy val dir = sourceDir / "main" / "models"
    val (modelName, fields) = parsed match {
      case (name: String, fields: Seq[_]) => (name.capitalize, fields.map(_.toString))
    }
    lazy val target = dir / (modelName + ".scala")

    val contents = engine.render("model/template.ssp", Map(
      ("packageName", "models"),
      ("modelName", modelName),
      ("fields", ModelInfo(fields))
    ))

    if (!dir.exists)
      IO.createDirectory(dir)
    if (!target.exists) {
      IO.write(target, contents)
      println("created: " + target)
    } else {
      println("exists: " + target)
    }
  }

  val help = "[ModelName] [field[:type] field[:type]]"
}

class ScaffoldGenerator extends Generator {
  def generate(info: GenerateInfo) {}
  val help = ""
}

