#play-soy-view

An experimental implementation of Google Closure library support for Play framework 2.2.x for Java

At the moment, this plugin assumes it will be wired via dependency injection, there is no support for including the plugin in plugins file in play that would result in auto wiring.
Individual components have to be wired via a dependency injection framework of choice.

# Example:
https://github.com/mati1979/play-soy-view-example

#Version:
0.1 - early adopters only
0.1.1 - minor bug fixes
0.1.3 - minor improvements
0.1.4 - minor improvements, e.g. AllowedUrls for ajax, introduced SoyViewConf interface and two implementations, play getting config values from play config and default where a user can configure values overriding defaults
0.1.5 - small bug fix in SoyHashesRuntimeResolver

# Maven Central:
This artifact is already available in maven central

#Sonatype release
$ sbt publishSigned

$ sbt sonatypeRelease
