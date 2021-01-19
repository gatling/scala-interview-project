package io.gatling.interview.model

import java.time.LocalDate

import io.circe._
import io.circe.generic.semiauto._

object Computer {
  implicit val decoder: Decoder[Computer] = deriveDecoder
  implicit val encoder: Encoder[Computer] = deriveEncoder
}

final case class Computer(
    id: Long,
    name: String,
    introduced: Option[LocalDate],
    discontinued: Option[LocalDate]
)
