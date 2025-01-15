ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.16"

lazy val root = (project in file("."))
  .settings(
    name := "keywordr-scala",
    idePackagePrefix := Some("com.keywordr")
  )

libraryDependencies += "org.apache.commons" % "commons-lang3" % "3.17.0"
