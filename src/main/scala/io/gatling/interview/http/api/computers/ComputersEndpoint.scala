package io.gatling.interview.http.api.computers

import io.gatling.interview.handler.ComputerHandler
import io.gatling.interview.model.Computer

import cats.effect.Effect
import io.finch._

class ComputersEndpoint[F[_]: Effect](computerHandler: ComputerHandler[F]) extends Endpoint.Module[F] {

  private val computers: Endpoint[F, Seq[Computer]] = get("computers") {
    computerHandler.queryComputers()
  }

  private[api] val endpoints = computers
}
