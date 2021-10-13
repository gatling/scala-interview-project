package io.gatling.interview.http.api.company

import cats.effect.Effect
import io.finch.Endpoint
import io.gatling.interview.dto.company.CompanyPrinterDTO
import io.gatling.interview.handler.company.CompanyHandler

class CompanyEndpoint[F[_] : Effect](companyHandler: CompanyHandler[F]) extends Endpoint.Module[F] {

  private val companies: Endpoint[F, Seq[CompanyPrinterDTO]] = get("companies") {
    companyHandler.findAll()
  }

  private[api] val companyEndpoints = companies

}
