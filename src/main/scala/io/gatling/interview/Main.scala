package io.gatling.interview

import cats.effect.{ExitCode, IO, IOApp, Outcome}
import org.typelevel.log4cats.slf4j.Slf4jLogger

object Main extends IOApp {

  private val logger = Slf4jLogger.getLogger[IO]

  override def run(args: List[String]): IO[ExitCode] =
    new App[IO].program(args).as(ExitCode.Success).guaranteeCase {
      case Outcome.Canceled()   => logger.warn("Execution has been canceled.")
      case Outcome.Errored(e)   => logger.error(e)("Unexpected error has occurred, exiting.")
      case Outcome.Succeeded(_) => logger.warn("Execution completed.")
    }
}
