package io.gatling.interview.repository

import io.gatling.interview.model.Computer

import cats.effect.Sync

import java.time.LocalDate
import io.gatling.interview.model.ComputerCreationRequest
import scala.util.Random

class ComputerRepository[F[_]](implicit F: Sync[F]) {
  def fetchAll(): F[Seq[Computer]] = F.pure(
    ComputerRepository.dbComputer
  )

  def insert(c: Computer): F[Unit] = F.pure(
    ComputerRepository.dbComputer =  ComputerRepository.dbComputer :+ c
  )
}

object ComputerRepository {
  // Fake DB !
  var dbComputer = Seq(Computer(1L, 1L, "Laptop", Some(LocalDate.of(1970, 10, 25)), None), Computer(2L, 1L, "toto", Some(LocalDate.of(1989, 10, 5)), Some(LocalDate.of(1998, 5, 15))))
}
