import sbt._
import Keys._

organization := "pl.matisoft"

name := "play-soy-view"

scalaVersion := "2.10.4"

lazy val root = (project in file(".")).enablePlugins(PlayJava)


libraryDependencies ++= Seq(
  "nathancomstock.github" % "closure-templates" % "2014-04-22",
  "com.google.javascript" % "closure-compiler" % "v20140625",
  "commons-io" % "commons-io" % "2.2"
)

publishMavenStyle := true

crossScalaVersions := Seq("2.10.4", "2.11.4")

resolvers += "nathancomstock-releases" at "https://github.com/nathancomstock/maven-repo/raw/master/releases"

resolvers += "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases"

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
    <connection>scm:git:git@github.com:matiwinnetou/play-soy-view.git</connection>
  </scm>
    <url>https://github.com/matiwinnetou/play-soy-view</url>
    <developers>
      <developer>
        <id>matiwinnetou</id>
        <name>Mateusz Szczap</name>
        <url>https://github.com/matiwinnetou</url>
      </developer>
    </developers>)
