ThisBuild / scalaVersion := "2.13.14"
ThisBuild / version := "1.0.0"
ThisBuild / organization := "io.gatling"
ThisBuild / organizationName := "Gatling Corp"

val disabledWarts = Set(
  Wart.Any, // cats
  Wart.NonUnitStatements, // test assertions
  Wart.TripleQuestionMark,
  Wart.DefaultArguments, // smithy
  Wart.StringPlusAny, // nice in string interpolation
  Wart.OptionPartial, // macro from smithy
)

lazy val root = (project in file("."))
  .enablePlugins(Smithy4sCodegenPlugin)
  .settings(
    name := "scala-interview-project",
    Compile / run / fork := true,
    Compile / run / connectInput := true,
    wartremoverWarnings ++= Warts.unsafe.filterNot(disabledWarts.contains),
    scalafmtOnCompile := true,
    scalacOptions ++= Seq(
      "-encoding",
      "UTF-8", // source files are in UTF-8
      "-deprecation", // warn about use of deprecated APIs
      "-Xfatal-warnings" // turn compiler warnings into errors
    ),
    libraryDependencies ++= Seq(
      "com.disneystreaming.smithy4s" %% "smithy4s-http4s"         % smithy4sVersion.value,
      "com.disneystreaming.smithy4s" %% "smithy4s-http4s-swagger" % smithy4sVersion.value,
      "org.http4s"                   %% "http4s-ember-server"     % "0.23.27",
      // Testing
      "org.scalatest"                %% "scalatest"               % "3.2.18"   % Test,
    )
  )
