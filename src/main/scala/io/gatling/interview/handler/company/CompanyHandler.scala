package io.gatling.interview.handler.company

import cats.effect.Sync
import cats.implicits._
import io.finch.{Ok, Output}
import io.gatling.interview.dto.company.CompanyPrinterDTO
import io.gatling.interview.service.Services

class CompanyHandler[F[_]](services: Services[F])(implicit F: Sync[F]) {

  def findAll(): F[Output[Seq[CompanyPrinterDTO]]] =
    services
      .findAllCompanies()
      .map(Ok)
}
