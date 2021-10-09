package io.gatling.interview.handler

import io.gatling.interview.model.Computer
import io.gatling.interview.repository.ComputerRepository

import cats.effect.Sync
import cats.implicits._
import io.finch.{ Ok, Output }

class ComputerHandler[F[_]](computerRepository: ComputerRepository[F])(implicit F: Sync[F]) {
  def queryComputers(): F[Output[Seq[Computer]]] =
    computerRepository
      .fetchAll()
      .map(Ok)

  def addComputer(c: Computer): F[Output[Unit]] =
    computerRepository.insert(c).map(Ok)
}
