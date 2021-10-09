package io.gatling.interview.model

import io.circe._
import io.circe.generic.semiauto._

object Company {
  implicit val decoder: Decoder[Company] = deriveDecoder
  implicit val encoder: Encoder[Company] = deriveEncoder
}

final case class Company(
    id: Long,
    name: String
)
