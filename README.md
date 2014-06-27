#play-soy-view

An experimental implementation of Google Closure library support for Play framework for Java

At the moment, this plugin assumes it will be wired via dependency injection, there is no support for including the plugin in plugins file in play that would result in auto wiring.
Individual components have to be wired via a dependency injection framework of choice or alternatively manually wired.

# Example:
https://github.com/mati1979/play-soy-view-example

# Version:
- 0.1 - early adopters only
- 0.1.1 - minor bug fixes
- 0.1.3 - minor improvements
- 0.1.4 - minor improvements, e.g. AllowedUrls for ajax, introduced SoyViewConf interface and two implementations, play getting * config values from play config and default where a user can configure values overriding defaults
- 0.1.5 - small bug fix in SoyHashesRuntimeResolver
- 0.1.6 - fixed a small regression bug in PlaySoyViewConfig
- 0.1.7 - minor bug fix to do with expire headers (typo) for ajax soy
- 0.1.8 - removal of checked exception in favour of unchecked (runtime) exceptions
- 0.1.12 - introduced ExceptionInTemplate from Play that shows line and column number of an error
- 0.1.13 - using play logger and removed slf4j

# TODO
- cross compile 2.3 version for Scala: 2.11.x

- Move code to java 8 or scala (rewrite), this is a Play plugin anyway

- make it idiomatic for play, make sure ClosurePlugin bootstraps a tree of components, but so that it is reconfigurable later as well. This requires that play properly supports DI in plugins (wait for 2.4.x)

- support soy files in app/views, there is an example go to do this based on Freemarker and play integration

- Try more than first accepted languages, if first one fails then proceed to next

- port ContentNegotiator from spring-soy-view

- include routes, where we have a soyJs endpoint defined

- JavaDoc

- Unit Test

- Convert messages from conf/messages automatically to xliffs

# Requirements:
- Java 7
- Play (Java or Scala) 2.2.x for version 0.13.play22 (Scala 2.10.x)
- Play (Java or Scala) 2.3.x for version 0.13.play23 (Scala 2.10.x)

# Maven Central:
This artifact is available in maven central (http://mvnrepository.com/artifact/pl.matisoft/play-soy-view_2.10/)
