package com.github.aselab.activerecord.scalatra

import sbt.complete.DefaultParsers._

object Parser {
  val controllerParser =
    Space ~> (token("controller" <~ Space) ~ (token(NotSpace, "controllerName") ~ spaceDelimited("action")))
}
