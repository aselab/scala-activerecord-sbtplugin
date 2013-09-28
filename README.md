# scala-activerecord-sbtplugin

Generator for Scala ActiveRecord

add following line to `project/plugins.sbt`

```scala
addSbtPlugin("com.github.aselab" % "scala-activerecord-sbtplugin" % "0.2.3-SNAPSHOT")
```

Inject plugin settings into project in build.sbt:

```scala
activerecordSettings
```

or project/Build.scala:

```scala
import sbt._
import Keys._

import com.github.aselab.activerecord.Plugin._

object MyBuild extends Build {
  lazy val root = Project("root", file("."),
    settings = Defaults.defaultSettings ++ activerecordSettings)
}
```

sbt-test

```sh
$ bin/sbt
> scripted
> scalatra/scripted
```

