package io.gatling.interview.model.company

import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.{Decoder, Encoder}

object Company {
  implicit val decoder: Decoder[Company] = deriveDecoder
  implicit val encoder: Encoder[Company] = deriveEncoder
}

case class Company(id: Long, name: String)



