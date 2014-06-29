import sbt._
import scala.Some

organization := "pl.matisoft"

name := "play-soy-view"

version := "0.1.15-SNAPSHOT"

scalaVersion := "2.10.4"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

libraryDependencies ++= Seq(
  "com.google.template" % "soy" % "2012-12-21",
  "com.google.javascript" % "closure-compiler" % "v20140625",
  "com.yahoo.platform.yui" % "yuicompressor" % "2.4.7",
  "commons-io" % "commons-io" % "2.2"
)

sonatypeSettings

releaseSettings

publishMavenStyle := true

//crossScalaVersions := Seq("2.10.4", "2.11.0")

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases" at nexus + "service/local/staging/deploy/maven2")
}

licenses := Seq("Apache-style" -> url("http://www.apache.org/licenses/LICENSE-2.0.html"))

pomIncludeRepository := { _ => false }

pomExtra := (
  <scm>
    <url>git@github.com:mati1979/play-soy-view.git</url>
    <connection>scm:git:git@github.com:mati1979/play-soy-view.git</connection>
  </scm>
    <url>https://github.com/mati1979/play-soy-view</url>
    <developers>
      <developer>
        <id>matiwinnetou</id>
        <name>Mateusz Szczap</name>
        <url>https://github.com/mati1979</url>
      </developer>
    </developers>)
