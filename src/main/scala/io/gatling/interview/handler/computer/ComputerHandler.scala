package io.gatling.interview.handler.computer

import cats.effect.Sync
import cats.implicits._
import io.finch.{Ok, Output}
import io.gatling.interview.dto.computer.{ComputerCreaterDTO, ComputerPrinterDTO}
import io.gatling.interview.model.computer.Computer
import io.gatling.interview.service.Services

class ComputerHandler[F[_]](computerService: Services[F])(implicit F: Sync[F]) {

  def updateComputer(computerId: Long, c: Computer): F[Output[Unit]] =
    computerService.update(computerId,c).map(Ok)


  def addComputer(c: ComputerCreaterDTO): F[Output[Unit]] =
    computerService.insert(c).map(Ok)

  def findAll(): F[Output[Seq[ComputerPrinterDTO]]] =
    computerService
      .findAll()
      .map(Ok)
}
