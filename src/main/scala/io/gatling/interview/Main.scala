package io.gatling.interview

import cats.effect.{ExitCase, ExitCode, IO, IOApp}
import io.chrisdavenport.log4cats.slf4j.Slf4jLogger

object Main extends IOApp {

  private val logger = Slf4jLogger.getLogger[IO]

  override def run(args: List[String]): IO[ExitCode] =
    new App[IO].program(args).as(ExitCode.Success).guaranteeCase {
      case ExitCase.Canceled  => logger.warn("Execution has been canceled.")
      case ExitCase.Error(e)  => logger.error(e)("Unexpected error has occurred, exiting.")
      case ExitCase.Completed => logger.warn("Execution completed.")
    }
}
