package io.gatling.interview.http.api.computers

import io.gatling.interview.handler.ComputerHandler
import io.gatling.interview.model.Computer

import cats.effect.Effect
import io.finch._
import io.finch.circe._
import io.gatling.interview.model.Computer._

class ComputersEndpoint[F[_]: Effect](computerHandler: ComputerHandler[F]) extends Endpoint.Module[F] {

  private val computers: Endpoint[F, Seq[Computer]] = get("computers") {
    computerHandler.queryComputers()
  }

  private val addComputer: Endpoint[F, Unit] = post("computers" :: jsonBody[Computer]) { c: Computer =>
    computerHandler.addComputer(c)
  }

  private[api] val endpoints = computers
  private[api] val postEndPoint = addComputer
}
