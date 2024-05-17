package io.gatling.interview.model

import java.time.LocalDate

final case class Computer(
    id: Long,
    name: String,
    introduced: Option[LocalDate],
    discontinued: Option[LocalDate]
)
