package io.gatling.interview.dto.computer

import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder

import java.time.LocalDate

object ComputerCreaterDTO {
  implicit val decoder: Decoder[ComputerCreaterDTO] = deriveDecoder
}

final case class ComputerCreaterDTO(
                                 name: String,
                                 introduced: Option[LocalDate],
                                 discontinued: Option[LocalDate],
                                 companyId: Option[Long]
                               )
