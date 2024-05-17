package io.gatling.interview.repository

import cats.effect._
import com.github.plokhotnyuk.jsoniter_scala.core._
import com.github.plokhotnyuk.jsoniter_scala.macros._
import io.gatling.interview.model.Computer

import java.nio.file.{Files, Path, Paths}

object ComputerRepository {
  val DefaultComputersFilePath: Path = Paths.get("computers.json")

  private implicit val computersCodec: JsonValueCodec[List[Computer]] = JsonCodecMaker.make

  def apply[F[_]: Async](path: Path = DefaultComputersFilePath): ComputerRepository[F] =
    new ComputerRepository[F](path)
}

class ComputerRepository[F[_]: Async](filePath: Path) {
  import ComputerRepository._

  def fetchAll(): F[List[Computer]] = Resource
    .fromAutoCloseable(Async[F].delay(Files.newInputStream(filePath)))
    .use { stream =>
      Async[F].delay(readFromStream[List[Computer]](stream))
    }

  def fetch(id: Long): F[Computer] = ???

  def insert(computer: Computer): F[Unit] = ???
}
