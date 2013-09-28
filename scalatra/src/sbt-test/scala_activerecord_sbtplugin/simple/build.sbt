val _version = "0.2.3-SNAPSHOT"

scalatraActiverecordSettings

libraryDependencies ++= Seq(
  "org.scalatra" %% "scalatra" % "2.2.0",
  "javax.servlet" % "javax.servlet-api" % "3.0.1",
  "com.github.aselab" % "scala-activerecord" % _version,
  "org.slf4j" % "slf4j-simple" % "1.7.5",
  "com.h2database" % "h2" % "1.3.173"
)

resolvers += Resolver.sonatypeRepo("snapshots")

