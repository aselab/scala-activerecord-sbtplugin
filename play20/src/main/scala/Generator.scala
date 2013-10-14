package com.github.aselab.activerecord.play

import com.github.aselab.activerecord._

import sbt._
import mojolly.inflector.InflectorImports._

class ControllerGenerator extends Generator {
  def generate(info: GenerateInfo) {
    import info._
    val (controllerName, actions) = parsed match {
      case (name: String, acts: Seq[_]) =>
        (name.capitalize, acts.map {
          case action: Seq[_] => action.map(_.toString)
        })
    }
    val target = sourceDir / "controllers" / (controllerName.pluralize.titleize + ".scala")

    val contents = engine.render("controller/template.ssp", Map(
      ("packageName", "controllers"),
      ("controllerName", controllerName.pluralize.titleize),
      ("modelName", controllerName.singularize.titleize),
      ("instanceName", controllerName.singularize.camelize)
    ))

    IOUtil.save(target, contents)
  }

  val help = "[controllerName] [action]*"
}

class RoutesGenerator extends Generator {
  def generate(info: GenerateInfo) {
    import info._
    val modelName = parsed.asInstanceOf[String]
    val target = file("./conf/routes")
    val parser = new Parser.PlayRoute(target)
    parser.insertedContents(modelName, engine).foreach(IOUtil.save(target, _))
  }

  val help = "[ModelName]"
}

