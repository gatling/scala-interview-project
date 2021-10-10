package io.gatling.interview.services

import io.gatling.interview.repository.ComputerRepository
import io.gatling.interview.model.Computer
import io.gatling.interview.model.ComputerCreationRequest
import cats.effect.Sync
import cats.implicits._

import scala.util.Random

class ComputerService[F[_]](computerRepository: ComputerRepository[F])(implicit F: Sync[F]) {
  def fetchAll(): F[Seq[Computer]] = 
    computerRepository.fetchAll()
  

  def insert(c: ComputerCreationRequest): F[Unit] = F.pure(
    // Simulate autogen of id in database.
    // If id is generated application side, a new service layer could be a good idea.
    computerRepository.insert(Computer(Random.nextLong(), c.companyId, c.name, c.introduced, c.discontinued))
  )
}
