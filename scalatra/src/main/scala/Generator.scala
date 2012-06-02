package com.github.aselab.activerecord.scalatra

import com.github.aselab.activerecord._

import sbt._

class ControllerGenerator extends Generator {
  def generate(info: GenerateInfo) {
    import info._
    val (controllerName, actions) = parsed match {
      case (name: String, acts: Seq[_]) =>
        (name.capitalize, acts.map {
          case action: Seq[_] => action.map(_.toString)
        })
    }
    val target = sourceDir / "controllers" / (controllerName + ".scala")

    val contents = engine.render("controller/template.ssp", Map(
      ("packageName", "controllers"),
      ("controllerName", controllerName),
      ("actions", actions)
    ))

    IOUtil.save(target, contents)
  }

  val help = "[controllerName] [action]*"
}

