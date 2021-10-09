package io.gatling.interview.http.api.company

import io.gatling.interview.handler.CompanyHandler
import io.finch.Endpoint
import io.gatling.interview.model.Company
import cats.effect.Effect

class CompanyEndpoint[F[_]: Effect](companyHandler: CompanyHandler[F]) extends Endpoint.Module[F] {

  private val company: Endpoint[F, Seq[Company]] = get("company") {
    companyHandler.queryCompany()
  }

  private[api] val getCompany = company
}
