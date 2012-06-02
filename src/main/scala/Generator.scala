package com.github.aselab.activerecord

import sbt._
import sbt.complete.DefaultParsers._

case class GenerateInfo(
  engine: ScalateTemplateEngine,
  sourceDir: File,
  parsed: Any
)(implicit val logger: Logger)

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
    val (modelName, fields) = parsed match {
      case (name: String, fields: List[_]) =>
        (name.capitalize, fields.map{
          case f: List[_] => f.map(_.toString)
        })
    }
    val target = sourceDir / "models" / (modelName + ".scala")

    val contents = engine.render("model/template.ssp", Map(
      ("packageName", "models"),
      ("modelName", modelName),
      ("fields", ModelInfo(fields))
    ))

    IOUtil.save(target, contents)
  }

  val help = "[ModelName] [field[:type] field[:type]]"
}

