name := "play-soy-view"

version := "0.1"

organization := "com.github.mati1979"

libraryDependencies ++= Seq(
  "com.google.template" % "soy" % "2012-12-21",
  "com.google.javascript" % "closure-compiler" % "v20130411",
  "com.yahoo.platform.yui" % "yuicompressor" % "2.4.7",
  "commons-io" % "commons-io" % "2.2"
)

play.Project.playJavaSettings