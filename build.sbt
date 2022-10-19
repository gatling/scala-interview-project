name := "scala-interview-project"

version := "1.0.0"
scalaVersion := "2.13.8"

lazy val scalatestVersion = "3.0.5"
lazy val logbackClassicVersion = "1.4.3"
lazy val circeVersion = "0.14.3"

run / fork := true

val disabledWarts = Set(
  Wart.Any, // cats
  Wart.NonUnitStatements // test assertions
)

wartremoverWarnings ++= Warts.unsafe.filterNot(disabledWarts.contains)

scalafmtOnCompile := true

scalacOptions ++= Seq(
  "-encoding",
  "UTF-8", // source files are in UTF-8
  "-deprecation", // warn about use of deprecated APIs
  "-Xfatal-warnings" // turn compiler warnings into errors
)

libraryDependencies ++= Seq(
  // Cats / Cats Effect
  "org.typelevel" %% "cats-core"   % "2.8.0",
  "org.typelevel" %% "cats-effect" % "3.3.14",
  // Circe
  "io.circe" %% "circe-core"    % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-parser"  % circeVersion,
  // Logging
  "org.typelevel"     %% "log4cats-slf4j"  % "2.3.2",
  "ch.qos.logback"     % "logback-classic" % logbackClassicVersion % Runtime,
  // Testing
  "org.scalatest"     %% "scalatest"                      % "3.2.14"   % Test,
  "org.scalatestplus" %% "scalacheck-1-15"                % "3.2.11.0" % Test,
  "org.scalacheck"    %% "scalacheck"                     % "1.17.0"   % Test,
  "org.typelevel"     %% "cats-effect-testing-scalatest"  % "1.4.0"    % Test
)
