package com.github.aselab.activerecord

case class ModelInfo(
  name: String,
  typeName: String,
  annotations: Seq[String] = Nil
) {
  override def toString =
    if (annotations.isEmpty)
      "%s: %s".format(name, typeName)
    else
      "%s %s: %s".format(annotations.map("@%s".format(_)).mkString(" "), name, typeName)

  def param = "@param %s ".format(name)
}

object ModelInfo {
  def apply(fields: Seq[String]): Seq[ModelInfo] = {
    fields.map(_.split(":").toList) map {
      case List(name, typeName) =>
        ModelInfo(name, typeName.capitalize) // properly implement later
      case List(name, typeName, option @ _*) =>
        ModelInfo(name, typeName.capitalize, option.map(_.capitalize)) // properly implement later
      case _ => throw new Exception("parse error")
    }
  }
}

