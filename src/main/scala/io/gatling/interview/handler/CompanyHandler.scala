package io.gatling.interview.handler

import cats.effect.Sync
import io.finch.{ Ok, Output }
import io.gatling.interview.model.Company
import io.gatling.interview.repository.CompanyRepository
import cats.implicits._


class CompanyHandler[F[_]](companyRepository: CompanyRepository[F])(implicit F: Sync[F]) {
  def queryCompany(): F[Output[Seq[Company]]] =
    companyRepository
      .fetchAll()
      .map(Ok)
}
