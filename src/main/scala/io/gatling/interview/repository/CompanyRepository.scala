package io.gatling.interview.repository

import cats.effect.Sync
import io.gatling.interview.model.Company

class CompanyRepository[F[_]](implicit F: Sync[F]) {
  def fetchAll(): F[Seq[Company]] = F.pure(
    CompanyRepository.dbCompany
  )
}

object CompanyRepository {
  // Fake DB !
  var dbCompany = Seq(Company(1L, "gatling"), Company(2L, "world company"))
}
