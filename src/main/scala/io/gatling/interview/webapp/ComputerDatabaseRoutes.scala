package io.gatling.interview.webapp

import cats.effect._
import io.gatling.interview.api._

object ComputerDatabaseRoutes extends ComputerDatabaseEndpoints[IO] {
  override def listComputers(): IO[ComputersOutput] = IO.pure {
    ComputersOutput(
      List(
        Computer(
          id = 1,
          name = "Sample computer",
          introduced = None,
          discontinued = None
        )
      )
    )
  }
}
