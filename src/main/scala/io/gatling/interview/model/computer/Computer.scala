package io.gatling.interview.model.computer

import io.circe._
import io.circe.generic.semiauto._
import io.gatling.interview.dto.computer.ComputerPrinterDTO

import java.time.LocalDate

object Computer {
  implicit val decoder: Decoder[Computer] = deriveDecoder
  implicit val simpleEncoder: Encoder[Computer] = deriveEncoder

  def computerMapper = (computer: Computer) =>
    ComputerPrinterDTO(
      computer.id,
      computer.name,
      computer.introduced,
      computer.discontinued,
      computer.companyId
    )
}

final case class Computer(
                           id: Long,
                           name: String,
                           introduced: Option[LocalDate],
                           discontinued: Option[LocalDate],
                           companyId: Option[Long]
                         )