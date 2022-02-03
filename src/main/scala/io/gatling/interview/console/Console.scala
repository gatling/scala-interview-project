package io.gatling.interview.console

import cats.effect.Sync

class Console[F[_]](implicit F: Sync[F]) {

  def println(s: String): F[Unit] = F.delay(scala.Console.println(s))
}
