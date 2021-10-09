package io.gatling.interview.model

import io.circe._
import io.circe.generic.semiauto._

import java.time.LocalDate
import java.time.temporal.ChronoUnit.DAYS

object Computer {
  implicit val decoder: Decoder[ComputerCreationRequest] = deriveDecoder

  implicit val encoder: Encoder[Computer] = new Encoder[Computer] {
    final def apply(c: Computer): Json = Json.obj(
      ("id", Json.fromLong(c.id)),
      ("name", Json.fromString(c.name)),
      ("introduced", c.introduced match {
        case Some(i) => Json.fromString(i.toString())
        case None => Json.Null
      }),
      ("discontinued", c.discontinued match {
        case Some(d) => Json.fromString(d.toString())
        case None => Json.Null
      }),
      ("lifetime", c.lifetime match {
        case Some(l) => Json.fromString(l.toString())
        case None => Json.Null
      }),
    ).mapObject(json => json.filter{case (key,value) => !value.isNull})
  }
}

final case class ComputerCreationRequest(
    companyId: Long,
    name: String,
    introduced: Option[LocalDate],
    discontinued: Option[LocalDate]
)

final case class Computer(
    id: Long,
    companyId: Long,
    name: String,
    introduced: Option[LocalDate],
    discontinued: Option[LocalDate]
) {
  lazy val lifetime: Option[Long] = for {
    i <- introduced
    d <- discontinued
  }
  yield (DAYS.between(i, d))
}

