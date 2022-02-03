package io.gatling.interview.handler

import io.gatling.interview.model.Computer
import io.gatling.interview.repository.ComputerRepository
import cats.effect.Sync
import io.gatling.interview.command.{ComputerCommand, ListComputers}
import io.gatling.interview.console.Console
import cats.implicits._

class ComputerHandler[F[_]](computerRepository: ComputerRepository[F], console: Console[F])(implicit F: Sync[F]) {

  def handle(command: ComputerCommand): F[Unit] =
    command match {
      case ListComputers =>
        for {
          computers <- computerRepository.fetchAll()
          output = computers
            .map { c =>
              val introduced = c.introduced.map(d => s", introduced: $d").getOrElse("")
              val discontinued = c.discontinued.map(d => s", discontinued: $d").getOrElse("")
              s"- [${c.id}] ${c.name}$introduced$discontinued"
            }
            .mkString("\n")
          _ <- console.println(output)
        } yield ()
    }

  def queryComputers(): F[Seq[Computer]] =
    computerRepository.fetchAll()
}
