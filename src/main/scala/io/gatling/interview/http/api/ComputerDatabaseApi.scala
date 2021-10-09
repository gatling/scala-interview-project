package io.gatling.interview.http.api

import io.gatling.interview.handler.ComputerHandler
import io.gatling.interview.handler.CompanyHandler
import io.gatling.interview.http.api.computers.ComputersEndpoint
import io.gatling.interview.http.api.company.CompanyEndpoint

import cats.effect.{ ContextShift, Effect }
import com.twitter.finagle.Service
import com.twitter.finagle.http.{ Request, Response }
import io.finch._
import io.finch.circe._

class ComputerDatabaseApi[F[_]: Effect: ContextShift](
  computerHandler: ComputerHandler[F],
  companyHandler: CompanyHandler[F]
) extends Endpoint.Module[F] {

  private val computersApi: ComputersEndpoint[F] = new ComputersEndpoint[F](computerHandler)
  private val companyApi: CompanyEndpoint[F] = new CompanyEndpoint[F](companyHandler)
  val service: Service[Request, Response] =
    Endpoint.toService(
      Bootstrap
        .serve[Application.Json](computersApi.endpoints)
        .serve[Application.Json](computersApi.postEndPoint)
        .serve[Application.Json](companyApi.getCompany)
        .compile
    )
}
