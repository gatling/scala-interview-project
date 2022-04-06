package io.gatling.interview

import io.gatling.interview.handler.ComputerHandler
import io.gatling.interview.repository.ComputerRepository
import cats.effect._
import cats.implicits._
import io.chrisdavenport.log4cats.slf4j.Slf4jLogger
import io.gatling.interview.command.ComputerCommand
import io.gatling.interview.console.Console

final class App[F[_]: ContextShift: Timer](implicit F: ConcurrentEffect[F]) {

  private val logger = Slf4jLogger.getLogger[F]

  def program(args: List[String]): F[Unit] =
    Blocker[F].use { blocker =>
      val repository = new ComputerRepository(ComputerRepository.DefaultComputersFilePath, blocker)
      val console = new Console[F]
      val handler = new ComputerHandler(repository, console)

      for {
        _ <- logger.debug(s"args: ${args.toString}")
        command <- F.fromOption(
          ComputerCommand.parse(args),
          new IllegalArgumentException(s"Cannot parse arguments ${args.toString}")
        )
        _ <- handler.handle(command)
      } yield ()
    }
}
