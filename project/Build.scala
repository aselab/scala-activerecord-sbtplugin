import sbt._
import Keys._
import PlayProject._

object Build extends Build {
  lazy val main = Project("main", file("."), settings = mainSettings)

  lazy val mainDependencies = Seq(
    "play" %% "play" % "2.0.1"
  )

  lazy val mainSettings: Seq[Project.Setting[_]] =
    Defaults.defaultSettings ++ ScriptedPlugin.scriptedSettings ++ Seq(
      libraryDependencies ++= mainDependencies,
      sourceGenerators in Compile <+= (scalaSource in Compile, sourceManaged in Compile) map ScalaTemplates,
      sbtPlugin := true,
      name := "scala-activerecord-sbtplugin",
      organization := "com.github.aselab",
      version := "0.1-SNAPSHOT",
      scalacOptions := Seq("-deprecation", "-unchecked"),
      ScriptedPlugin.scriptedBufferLog := false,
      watchSources <++= (ScriptedPlugin.sbtTestDirectory).map{ dir => (dir ***).get },
      initialCommands in console := Seq(
        "com.github.aselab"
      ).map{"import " + _ + "._"}.mkString("\n")
    )

  val ScalaTemplates = (sourceDirectory: File, generatedDir: File) => {
    import play.templates._
    (generatedDir ** "*.template.scala").get.map(GeneratedSource(_)).foreach(_.sync())
    try {
      val templateType = "play.api.templates.Html"
      val templateFormat = "play.api.templates.HtmlFormat"
      val additionalImports = ""
      (sourceDirectory ** "*.scala.*").get.foreach { template =>
        ScalaTemplateCompiler.compile(template, sourceDirectory, generatedDir,
          templateType, templateFormat, additionalImports)
      }
    } catch {
      case TemplateCompilationError(source, message, line, column) => {
        throw TemplateCompilationException(source, message, line, column - 1)
      }
      case e => throw e
    }
    (generatedDir ** "*.scala").get.map(_.getAbsoluteFile)
  }
}

