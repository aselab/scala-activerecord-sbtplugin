scalatraActiverecordSettings

libraryDependencies ++= Seq(
  "org.scalatra" %% "scalatra" % "2.0.4",
  "javax.servlet" % "javax.servlet-api" % "3.0.1",
  "com.github.aselab" % "scala-activerecord" % "0.1",
  "org.slf4j" % "slf4j-simple" % "1.6.4",
  "com.h2database" % "h2" % "1.3.166"
)

resolvers ++= Seq(
  "aselab repo" at "http://aselab.github.com/maven/",
  "typesafe repo" at "http://repo.typesafe.com/typesafe/releases/"
)
