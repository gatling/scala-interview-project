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

libraryDependencies ++= Seq(
  // Testing
  "org.scalatest"                %% "scalatest"               % "3.2.18"   % Test,
)
