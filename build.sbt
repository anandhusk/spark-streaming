ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.12.20"

lazy val root = (project in file("."))
  .settings(
    name := "spark-streaming"
  )

libraryDependencies += "org.apache.spark" %% "spark-sql" % "3.3.0"

libraryDependencies += "org.apache.spark" %% "spark-core" % "3.3.0"


libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.12" % Test

libraryDependencies += "org.xerial" % "sqlite-jdbc" % "3.47.0.0"

libraryDependencies += "com.h2database" % "h2" % "2.2.224"




