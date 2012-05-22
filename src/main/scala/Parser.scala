package com.github.aselab.activerecord

import sbt.complete.DefaultParsers._

object Parser {

  val modelParser =
    Space ~> (token("model" <~ Space) ~ (token(NotSpace, "modelName") ~ fields))

  lazy val fields = (token(Space) ~> (fieldName ~ fieldType ~ options).map{
    case (x ~ y ~ z) => (x +: y +: z).mkString(":")
  }).* <~ SpaceClass.*

  lazy val Field = charClass(s => !s.isWhitespace && !(s == ':')).+.string
  lazy val fieldName = token(Field <~ token(':'), "fieldName")
  lazy val fieldType = token(Field).examples(ModelInfo.allTypes: _*)
  lazy val options = (token(':') ~> token(Field).examples(ModelInfo.allOptions: _*)).*

  val scaffoldParser =
    Space ~> ((token("scaffold" <~ Space) ~ token(NotSpace, "modelName")))

  def default(name: String) = Space ~> (token(name) ~ spaceDelimited("args*"))
}
