package io.gatling.interview.handler

import io.gatling.interview.model.Computer
import io.gatling.interview.model.ComputerCreationRequest

import cats.effect.Sync
import cats.implicits._
import io.finch.{ Ok, Output }
import io.gatling.interview.services.ComputerService

class ComputerHandler[F[_]](computerService: ComputerService[F])(implicit F: Sync[F]) {
  def queryComputers(): F[Output[Seq[Computer]]] =
    computerService
      .fetchAll()
      .map(Ok)

  def addComputer(c: ComputerCreationRequest): F[Output[Unit]] =
    computerService.insert(c).map(Ok)
}
