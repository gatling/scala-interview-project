package io.gatling.interview.http.api

import io.gatling.interview.handler.ComputerHandler
import io.gatling.interview.http.api.computers.ComputersEndpoint

import cats.effect.{ ContextShift, Effect }
import com.twitter.finagle.Service
import com.twitter.finagle.http.{ Request, Response }
import io.finch._
import io.finch.circe._

class ComputerDatabaseApi[F[_]: Effect: ContextShift](
    computerHandler: ComputerHandler[F]
) extends Endpoint.Module[F] {

  private val computersApi: ComputersEndpoint[F] = new ComputersEndpoint[F](computerHandler)

  val service: Service[Request, Response] =
    Endpoint.toService(
      Bootstrap
        .serve[Application.Json](computersApi.endpoints)
        .compile
    )
}
