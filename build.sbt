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
      "com.disneystreaming.smithy4s" %% "smithy4s-http4s"                % smithy4sVersion.value,
      "com.disneystreaming.smithy4s" %% "smithy4s-http4s-swagger"        % smithy4sVersion.value,
      "org.http4s"                   %% "http4s-ember-server"            % "0.23.27",
      // same version as in smithy4s
      // scope should be "compile-internal" but IntelliJ does not understand it https://youtrack.jetbrains.com/issue/SCL-18284
      "com.github.plokhotnyuk.jsoniter-scala" %% "jsoniter-scala-macros" % "2.27.6" % "provided",
      // Testing
      "org.scalatest"                %% "scalatest"                      % "3.2.18"   % Test,
      "org.typelevel"                %% "cats-effect-testing-scalatest"  % "1.5.0"    % Test
    )
  )
