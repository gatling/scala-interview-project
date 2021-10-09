package io.gatling.interview.handler

import cats.effect.Sync
import io.finch.{ Ok, Output }
import io.gatling.interview.repository.CompanyRepository
import io.gatling.interview.model.Company

class CompanyHandler[F[_]](companyRepository: CompanyRepository[F])(implicit F: Sync[F]) {
  def queryComputers(): F[Output[Seq[Company]]] =
    companyRepository.fetchAll().map(Ok)
}
