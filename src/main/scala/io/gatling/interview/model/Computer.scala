package io.gatling.interview.model

import io.circe._
import io.circe.generic.semiauto._

import java.time.LocalDate
import java.time.temporal.ChronoUnit.DAYS

object Computer {
  implicit val decoder: Decoder[ComputerCreationRequest] = deriveDecoder

  implicit val encoder: Encoder[Computer] = (c: Computer) => Json.obj(
    ("id", Json.fromLong(c.id)),
    ("companyId", Json.fromLong(c.companyId)),
    ("name", Json.fromString(c.name)),
    ("introduced", c.introduced.map(i => Json.fromString(i.toString)).getOrElse(Json.Null)),
    ("discontinued", c.discontinued.map(d => Json.fromString(d.toString)).getOrElse(Json.Null)),
    ("lifetime", c.lifetime.map(l => Json.fromString(l.toString)).getOrElse(Json.Null)),
  ).mapObject(json => json.filter { case (_, value) => !value.isNull })
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
  yield DAYS.between(i, d)
}

