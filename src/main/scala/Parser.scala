package com.github.aselab.activerecord

import sbt.complete.DefaultParsers._

object Parser {

  val modelParser =
    Space ~> ((token("model" <~ Space) ~ (token(NotSpace, "modelName") ~ spaceDelimited("[field:type:options]*"))))

  val scaffoldParser =
    Space ~> ((token("scaffold" <~ Space) ~ token(NotSpace, "modelName")))

  def default(name: String) = Space ~> (token(name) ~ spaceDelimited("args*"))
}
