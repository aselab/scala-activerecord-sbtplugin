package com.github.aselab.activerecord

import sbt._

object IOUtil {

  implicit def enumToIterator[A](e : java.util.Enumeration[A]) = new Iterator[A] {
    def next = e.nextElement
    def hasNext = e.hasMoreElements
  }

  def baseTemplateDir(baseDirectory: File, templateDir: String): File =
    if (templateDir.startsWith("/"))
      file(templateDir)
    else
      baseDirectory / templateDir

  def copyJarResourses(url: java.net.URL, destination: String, logger: Logger) {
    url.openConnection match {
      case connection: java.net.JarURLConnection =>
        val entryName = connection.getEntryName
        val jarFile = connection.getJarFile
        jarFile.entries.filter(_.getName.startsWith(entryName)).foreach { e =>
          val fileName = e.getName.drop(entryName.size)
          val target = file(destination) / fileName
          if (target.exists) {
            logger.warn("already exists: " + target)
          } else {
            if (!e.isDirectory)
              IO.transfer(jarFile.getInputStream(e), target)
            else
              IO.createDirectory(target)
            logger.success("created: " + target)
          }
        }
      }
    }
}

