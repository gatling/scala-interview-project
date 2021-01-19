package io.gatling.interview.repository

import io.gatling.interview.model.Computer

import cats.effect.Sync

import java.time.LocalDate

class ComputerRepository[F[_]](implicit F: Sync[F]) {
  def fetchAll(): F[Seq[Computer]] = F.pure(
    Seq(Computer(1L, "Laptop", Some(LocalDate.of(1970, 10, 25)), None), Computer(2L, "toto", Some(LocalDate.of(1989, 10, 5)), Some(LocalDate.of(1998, 5, 15))))
  )

  def insert(): F[Unit] = F.unit
}
