import sbt.Global

name := "scala-interview-project"

version := "1.0.0"
scalaVersion := "2.13.8"

lazy val scalatestVersion = "3.0.5"
lazy val logbackClassicVersion = "1.2.10"
lazy val circeVersion = "0.14.1"
lazy val finchVersion = "0.32.1"
lazy val pureconfigVersion = "0.14.1"

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
  "org.typelevel" %% "cats-core"   % "2.7.0",
  "org.typelevel" %% "cats-effect" % "2.5.4",
  "org.typelevel" %% "kittens"     % "2.3.2",
  "org.typelevel" %% "mouse"       % "1.0.9",
  // Circe
  "io.circe" %% "circe-core"    % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-parser"  % circeVersion,
  "io.circe" %% "circe-refined" % circeVersion,
  "io.circe" %% "circe-fs2"     % "0.14.0",
  // Logging
  "io.chrisdavenport" %% "log4cats-slf4j"  % "1.1.1",
  "ch.qos.logback"     % "logback-classic" % logbackClassicVersion % Runtime,
  // Testing
  "org.scalatest"     %% "scalatest"                      % "3.2.11"   % Test,
  "org.scalatestplus" %% "scalacheck-1-15"                % "3.2.11.0" % Test,
  "org.scalacheck"    %% "scalacheck"                     % "1.15.4"   % Test,
  "com.codecommit"    %% "cats-effect-testing-scalatest"  % "0.5.4"    % Test
)

dependencyOverrides ++= Seq(
  "co.fs2" %% "fs2-core" % "2.5.10", // Avoid FS2 3.x, stay on 2.x with cats-effect 2.x
)
