package com.github.aselab.activerecord.play

import com.github.aselab.activerecord._
import sbt.complete.DefaultParsers._
import com.github.aselab.activerecord.Parser.Field
import java.io._
import scala.io.Source
import mojolly.inflector.InflectorImports._

object Parser {
  val controllerParser =
    Space ~> (token("controller" <~ Space) ~ (token(NotSpace, "controllerName") ~ actions))
  val routesParser =
    Space ~> (token("routes" <~ Space) ~ (token(NotSpace, "modelName")))

  lazy val actions = (token(Space) ~> (path ~ action).map{
    case (x ~ y) => List(x, y)
  }).* <~ SpaceClass.*

  lazy val path = token(Field <~ token(':'), "path:action   e.g.) /index:get")
  lazy val action = token(Field).examples("get", "post", "update", "delete")

  class PlayRoute(routesFile: File) {
    val lines = Source.fromFile(routesFile).getLines.toSeq

    def searchInsertPosition = {
      val insertRegex = "^[^#]*".r
      lines.zipWithIndex.collectFirst{ case (insertRegex(), i) => i }
    }

    def alreadyExists(modelName: String) = {
      val searchRegex = """^GET\s+/%s.*""".format(modelName.toLowerCase).r
      lines.collectFirst { case searchRegex() => true }.nonEmpty
    }

    def insertedContents(modelName: String, engine: ScalateTemplateEngine) = {
      val template = engine.render("routes/template.ssp", Map(
        "uri" -> modelName.pluralize.camelize,
        "controllerName" -> modelName.pluralize.titleize
      ))
      if (!alreadyExists(modelName)) {
        searchInsertPosition.map { i =>
          val (l, r) = lines.splitAt(i)
          (l ++ template.split("\\r?\\n") ++ List("") ++ r).mkString("\r\n")
        }
      } else {
        None
      }
    }
  }
}

