package io.gatling.interview.repository

import io.gatling.interview.model.Computer
import cats.effect.{Blocker, ContextShift, Sync}
import io.gatling.interview.repository.ComputerRepository.ComputersFileCharset

import java.nio.charset.{Charset, StandardCharsets}
import java.nio.file.{Files, Path, Paths}

import io.circe.parser.decode
import cats.implicits._

object ComputerRepository {
  val DefaultComputersFilePath: Path = Paths.get("computers.json")
  private val ComputersFileCharset: Charset = StandardCharsets.UTF_8
}

class ComputerRepository[F[_]: ContextShift](filePath: Path, blocker: Blocker)(implicit
    F: Sync[F]
) {

  def fetchAll(): F[Seq[Computer]] =
    for {
      json <- blocker.blockOn(F.delay {
        val jsonBytes = Files.readAllBytes(filePath)
        new String(jsonBytes, ComputersFileCharset)
      })
      computers <- F.fromEither(decode[Seq[Computer]](json))
    } yield computers

  def fetch(id: Long): F[Computer] = ???

  def insert(computer: Computer): F[Unit] = ???
}
