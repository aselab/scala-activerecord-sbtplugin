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
  lazy val fileName = name.capitalize + ".scala"
  lazy val modelFile = dir / fileName

  def generate {
    val contents = engine.render("model/template.ssp", Map())
    if (!dir.exists)
      IO.createDirectory(dir)
    if (!modelFile.exists) {
      IO.write(modelFile, contents)
      println("created: " + modelFile)
    } else {
      println("exists: " + modelFile)
    }
  }
}

class ScaffoldGenerator(implicit val info: GenerateInfo) extends Generator {
  def generate {}
}

