package io.gatling.interview.dto.computer

import io.circe._
import io.circe.generic.semiauto._
import io.circe.syntax.EncoderOps

import java.time.LocalDate
import java.time.temporal.ChronoUnit

object ComputerPrinterDTO {
  implicit val decoder: Decoder[ComputerPrinterDTO] = deriveDecoder
  implicit val encoderLifetime: Encoder[ComputerPrinterDTO] = Encoder.instance(
    c =>
      deriveEncoder[ComputerPrinterDTO]
        .apply(c)
        .mapObject(jo => jo.add("lifetime", c.lifetime.asJson))
  )
}

final case class ComputerPrinterDTO(
                                     id: Long,
                                     name: String,
                                     introduced: Option[LocalDate],
                                     discontinued: Option[LocalDate],
                                     companyName: Option[String]
                         ) {
  val lifetime: Option[Long] = {
    if (introduced.isDefined && discontinued.isDefined) {
      Some(ChronoUnit.DAYS.between(introduced.get, discontinued.get))
    } else {
      None
    }
  }
}
