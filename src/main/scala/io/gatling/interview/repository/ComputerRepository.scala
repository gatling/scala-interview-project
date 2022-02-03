package io.gatling.interview.repository

import io.gatling.interview.model.Computer
import cats.effect.{Blocker, ContextShift, Sync}
import io.gatling.interview.repository.ComputerRepository.{ComputersFileCharset, ComputersFilePath}

import java.nio.charset.{Charset, StandardCharsets}
import java.nio.file.{Files, Path, Paths}

import io.circe.parser.decode
import cats.implicits._

object ComputerRepository {
  private val ComputersFilePath: Path = Paths.get("computers.json")
  private val ComputersFileCharset: Charset = StandardCharsets.UTF_8
}

class ComputerRepository[F[_] : ContextShift](blocker: Blocker)(implicit F: Sync[F]) {

  def fetchAll(): F[Seq[Computer]] =
    for {
      json <- blocker.blockOn(F.delay(Files.readString(ComputersFilePath, ComputersFileCharset)))
      computers <- F.fromEither(decode[Seq[Computer]](json))
    } yield computers

  def fetch(id: Long): F[Computer] = F.delay(???)

  def insert(): F[Unit] = F.delay(???)
}
