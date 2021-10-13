package io.gatling.interview.http.api

import cats.effect.{ContextShift, Effect}
import com.twitter.finagle.Service
import com.twitter.finagle.http.{Request, Response}
import io.finch._
import io.finch.circe._
import io.gatling.interview.handler.company.CompanyHandler
import io.gatling.interview.handler.computer.ComputerHandler
import io.gatling.interview.http.api.company.CompanyEndpoint
import io.gatling.interview.http.api.computers.ComputersEndpoint

class ComputerDatabaseApi[F[_] : Effect : ContextShift](
                                                         computerHandler: ComputerHandler[F], companyHandler: CompanyHandler[F]
                                                       ) extends Endpoint.Module[F] {

  private val computersApi: ComputersEndpoint[F] = new ComputersEndpoint[F](computerHandler)
  private val companiesApi: CompanyEndpoint[F] = new CompanyEndpoint[F](companyHandler)

  val service: Service[Request, Response] =
    Endpoint.toService(
      Bootstrap
        .serve[Application.Json](computersApi.endpoints)
        .serve[Application.Json](companiesApi.companyEndpoints)
        .compile
    )
}
