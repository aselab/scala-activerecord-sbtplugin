package com.github.aselab.activerecord

import sbt._

case class GenerateInfo(
  engine: ScalateTemplateEngine,
  sourceDir: File,
  name: String,
  fields: Seq[String]
)

trait Generator {
  val info: GenerateInfo

  val (engine, sourceDir, name, fields) = info match {
    case GenerateInfo(t, s, n, f) => (t, s, n, f)
  }

  def generate: Unit
}

class ModelGenerator(implicit val info: GenerateInfo) extends Generator {
  lazy val dir = sourceDir / "main" / "models"
  lazy val modelName = name.capitalize
  lazy val target = dir / (modelName + ".scala")

  def generate {
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
}

class ScaffoldGenerator(implicit val info: GenerateInfo) extends Generator {
  def generate {}
}

