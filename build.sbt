name := "scala-interview-project"

version := "1.0.0"
scalaVersion := "2.13.14"

run / fork := true

val disabledWarts = Set(
  Wart.Any, // cats
  Wart.NonUnitStatements, // test assertions
  Wart.TripleQuestionMark
)

wartremoverWarnings ++= Warts.unsafe.filterNot(disabledWarts.contains)

scalafmtOnCompile := true

scalacOptions ++= Seq(
  "-encoding",
  "UTF-8", // source files are in UTF-8
  "-deprecation", // warn about use of deprecated APIs
  "-Xfatal-warnings" // turn compiler warnings into errors
)

lazy val circeVersion = "0.14.7"

libraryDependencies ++= Seq(
  // Cats / Cats Effect
  "org.typelevel" %% "cats-core"   % "2.10.0",
  "org.typelevel" %% "cats-effect" % "3.5.4",
  // Circe
  "io.circe" %% "circe-core"    % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-parser"  % circeVersion,
  // Logging
  "org.typelevel"     %% "log4cats-slf4j"  % "2.7.0",
  "ch.qos.logback"     % "logback-classic" % "1.5.6" % Runtime,
  // Testing
  "org.scalatest"     %% "scalatest"                      % "3.2.18"   % Test,
  "org.scalatestplus" %% "scalacheck-1-18"                % "3.2.18.0" % Test,
  "org.scalacheck"    %% "scalacheck"                     % "1.18.0"   % Test,
  "org.typelevel"     %% "cats-effect-testing-scalatest"  % "1.5.0"    % Test
)
