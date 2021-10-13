package io.gatling.interview.dto.company

import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.{Decoder, Encoder}
import io.gatling.interview.model.computer.Computer

object CompanyPrinterDTO {
  implicit val decoder: Decoder[CompanyPrinterDTO] = deriveDecoder
  implicit val simpleEncoder: Encoder[CompanyPrinterDTO] = deriveEncoder
}

final case class CompanyPrinterDTO(id: Long, name: String, computers: Option[Seq[Computer]])
