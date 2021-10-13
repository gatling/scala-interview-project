package io.gatling.interview.http.api.computers

import cats.effect.Effect
import io.finch._
import io.finch.circe._
import io.gatling.interview.dto.computer.{ComputerCreaterDTO, ComputerPrinterDTO}
import io.gatling.interview.handler.computer.ComputerHandler
import io.gatling.interview.model.computer.Computer

class ComputersEndpoint[F[_] : Effect](computerHandler: ComputerHandler[F]) extends Endpoint.Module[F] {

  private val computers: Endpoint[F, Seq[ComputerPrinterDTO]] = get("computers") {
    computerHandler.findAll()
  }

  private val createComputer: Endpoint[F, Unit] = post("computer" :: jsonBody[ComputerCreaterDTO]) { c: ComputerCreaterDTO =>
    computerHandler.addComputer(c)
  }

  private val updateCompter: Endpoint[F, Unit] = put("computer" :: path[Long] :: jsonBody[Computer]) { (computerId: Long, c: Computer) =>
    computerHandler.updateComputer(computerId, c)
  }

  private val deleteComputer: Endpoint[F, Unit] = delete("computer" :: path[Long]) { computerId: Long =>
    val err = "TODO implement delete computer. Your computer with id:" + computerId + " is not delete !!"
    NoContent[Unit].withHeader("X-String" -> err)
  }

  private[api] val endpoints = computers :+: createComputer :+: updateCompter :+: deleteComputer
}
