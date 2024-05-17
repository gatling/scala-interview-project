package io.gatling.interview.webapp

import cats.effect._
import io.gatling.interview.api._
import io.gatling.interview.api
import io.gatling.interview.repository.ComputerRepository
import io.gatling.interview.model

import java.time.LocalDate
import java.time.format.DateTimeFormatter

object ComputerDatabaseRoutes {
  private val localDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
  private def localDateToString(value: LocalDate): String = localDateFormatter.format(value)

  private def transformComputer(value: model.Computer): api.Computer = api.Computer(
    id = value.id,
    name = value.name,
    introduced = value.introduced.map(localDateToString),
    discontinued = value.discontinued.map(localDateToString)
  )
}

class ComputerDatabaseRoutes(repository: ComputerRepository[IO])
    extends ComputerDatabaseEndpoints[IO] {
  import ComputerDatabaseRoutes._

  override def listComputers(): IO[ComputersOutput] = for {
    result <- repository.fetchAll()
  } yield ComputersOutput(result.map(transformComputer))
}
