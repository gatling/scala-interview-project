import sbt.Global

name := "scala-interview-project"

version := "1.0.0"
scalaVersion := "2.13.4"

lazy val catsVersion = "2.3.1"
lazy val scalatestVersion = "3.0.5"
lazy val logbackClassicVersion = "1.2.3"
lazy val circeVersion = "0.13.0"
lazy val finchVersion = "0.32.1"
lazy val pureconfigVersion = "0.14.0"

val disabledWarts = Set(
  Wart.Any, // cats
  Wart.NonUnitStatements // test assertions
)

wartremoverWarnings ++= Warts.unsafe.filterNot(disabledWarts.contains)

scalacOptions ++= Seq(
  "-encoding",
  "UTF-8", // source files are in UTF-8
  "-deprecation", // warn about use of deprecated APIs
  "-Xfatal-warnings" // turn compiler warnings into errors
)

libraryDependencies ++= Seq(
  // Cats / Cats Effect
  "org.typelevel" %% "cats-core"   % catsVersion,
  "org.typelevel" %% "cats-effect" % catsVersion,
  "org.typelevel" %% "kittens"     % "2.2.1",
  "org.typelevel" %% "mouse"       % "0.26.2",
  // Finch / Scalatags
  "com.github.finagle" %% "finchx-core"    % finchVersion,
  "com.github.finagle" %% "finchx-circe"   % finchVersion,
  "com.github.finagle" %% "finchx-refined" % finchVersion,
  "com.lihaoyi"        %% "scalatags"      % "0.9.2",
  // Circe
  "io.circe" %% "circe-core"    % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-parser"  % circeVersion,
  "io.circe" %% "circe-refined" % circeVersion,
  "io.circe" %% "circe-fs2"     % circeVersion,
  // Pureconfig
  "com.github.pureconfig" %% "pureconfig"             % pureconfigVersion,
  "com.github.pureconfig" %% "pureconfig-cats-effect" % pureconfigVersion,
  // Logging
  "io.chrisdavenport" %% "log4cats-slf4j"  % "1.1.1",
  "ch.qos.logback"     % "logback-classic" % logbackClassicVersion % Runtime,
  // Testing
  "org.scalatest"     %% "scalatest"       % "3.2.3"   % Test,
  "org.scalatestplus" %% "scalacheck-1-15" % "3.2.3.0" % Test,
  "org.scalacheck"    %% "scalacheck"      % "1.15.2"  % Test
)
