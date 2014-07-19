#play-soy-view

An implementation of Google Closure library support for Play framework for Java

At the moment, this plugin assumes it will be wired via dependency injection, there is no support for including the plugin in plugins file in play that would result in auto wiring.
Individual components have to be wired via a dependency injection framework of choice or alternatively manually wired.

# Example:
https://github.com/mati1979/play-soy-view-example

# Version:
- 0.1 - early adopters only
- 0.1.1 - minor bug fixes2
- 0.1.3 - minor improvements
- 0.1.4 - minor improvements, e.g. AllowedUrls for ajax, introduced SoyViewConf interface and two implementations, play getting * config values from play config and default where a user can configure values overriding defaults
- 0.1.5 - small bug fix in SoyHashesRuntimeResolver
- 0.1.6 - fixed a small regression bug in PlaySoyViewConfig
- 0.1.7 - minor bug fix to do with expire headers (typo) for ajax soy
- 0.1.8 - removal of checked exception in favour of unchecked (runtime) exceptions
- 0.1.12 - introduced ExceptionInTemplate from Play that shows line and column number of an error
- 0.1.13 - using play logger and removed slf4j
- 0.1.14 - improved debug logging, moved o java 8 and play 2.3 for older version of play use old versions
- 0.1.15 - improved debug logging
- 0.1.16 - shows compilation time for in case hot reload mode is on
- 0.1.18 - introduced a short lived cache (10 sec by default) for compiled templates in dev mode (hot reload mode), removed yahoo minimification, one library less

# TODO
- get rid of commons-io dependency

- consider splitting away core and web module since a lot of people may choose to use grunt or sbt -> soy to js compilation instead of ajax controller to compile soy to js

- upgrade to latest soy templates 04.2014 release (pending: https://github.com/google/closure-templates/issues/4)

- Adopt latest google closure templates features

- cross compile 2.3 version for Scala: 2.11.x

- When Play 2.4 is released think about improving library from point of view - dependency injection

- support soy files in app/views, there is an example go to do this based on Freemarker and play integration

- Try more than first accepted languages, if first one fails then proceed to next

- port ContentNegotiator from spring-soy-view

- port excluded properties for reflection to soy converter

- JavaDoc

- Unit Test

# Requirements:
- Java 7 or Java 8 in case of version >= 0.1.14
- Play (Java or Scala) 2.2.x for version 0.1.13 (Scala 2.10.x)
- Play (Java or Scala) 2.3.x for version >= 0.1.14 (Scala 2.10.x)

# Maven Central:
This artifact is available in maven central (http://mvnrepository.com/artifact/pl.matisoft/play-soy-view_2.10/)
