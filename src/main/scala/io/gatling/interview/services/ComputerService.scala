package io.gatling.interview.services

import io.gatling.interview.repository.ComputerRepository
import io.gatling.interview.model.Computer
import io.gatling.interview.model.ComputerCreationRequest
import cats.effect.Sync
import cats.implicits._

import scala.util.Random
import io.gatling.interview.repository.CompanyRepository

class ComputerService[F[_]](computerRepository: ComputerRepository[F], companyRepository: CompanyRepository[F])(implicit F: Sync[F]) {
  def fetchAll(): F[Seq[Computer]] = 
    computerRepository.fetchAll()
  
  def insert(c: ComputerCreationRequest): F[Unit] = {
    for {
      companies <- companyRepository.fetchAll()
      id <- {
        if (companies.map(_.id).contains(c.companyId)) {
          computerRepository.fetchAll().map(_.map(_.id).max + 1)
        } else {
          F.raiseError(new IllegalArgumentException("Unknown company"))
        }
      }
      _ <- computerRepository.insert(Computer(id, c.companyId, c.name, c.introduced, c.discontinued))
    }
    yield ()
  }

  def fetch(id: Long): F[Option[Computer]] =
    computerRepository.fetch(id)

}
