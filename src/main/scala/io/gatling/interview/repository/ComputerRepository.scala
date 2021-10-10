package io.gatling.interview.repository

import io.gatling.interview.model.Computer
import cats.effect.Sync

import java.time.LocalDate
import io.gatling.interview.model.ComputerCreationRequest
import scala.util.Random
import io.circe.parser

class ComputerRepository[F[_]](implicit F: Sync[F]) {
  def fetchAll(): F[Seq[Computer]] = F.pure(
    ComputerRepository.dbComputer
  )

  def insert(c: Computer): F[Unit] = F.pure(
    ComputerRepository.dbComputer =  ComputerRepository.dbComputer :+ c
  )

  def fetch(id: Long): F[Option[Computer]] = F.pure(
    ComputerRepository.dbComputer.find(_.id == id)
  )
}

object ComputerRepository {
  // Fake DB, loaded from a json resource I found in the project !
  var dbComputer = {
    val source = scala.io.Source.fromFile("src/main/resources/data/computers.json")
    val lines = try source.mkString finally source.close()
    parser.decode[Seq[Computer]](lines) match {
      case Right(computers) => computers
      case Left(ex) => Seq()
    }
  }
}
