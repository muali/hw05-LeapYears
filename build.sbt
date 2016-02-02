
val scalaV = "2.11.7"

lazy val commonSettings = Seq(
  version := "1.0",
  scalaVersion := scalaV)

lazy val converters = project.in(file("converters")).settings(commonSettings: _*).
  settings(
    name := "converters",
    libraryDependencies += "org.scala-lang" % "scala-compiler" % scalaV,
    libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaV,
    libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "1.0.3"
  )

lazy val root = project.in(file("")).dependsOn(converters).settings(commonSettings: _*)