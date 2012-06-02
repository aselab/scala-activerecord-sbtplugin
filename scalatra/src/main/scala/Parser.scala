package com.github.aselab.activerecord.scalatra

import sbt.complete.DefaultParsers._
import com.github.aselab.activerecord.Parser.Field

object Parser {
  val controllerParser =
    Space ~> (token("controller" <~ Space) ~ (token(NotSpace, "controllerName") ~ actions))

  lazy val actions = (token(Space) ~> (path ~ action).map{
    case (x ~ y) => List(x, y)
  }).* <~ SpaceClass.*

  lazy val path = token(Field <~ token(':'), "path:action   e.g.) /index:get")
  lazy val action = token(Field).examples("get", "post", "update", "delete")
}
