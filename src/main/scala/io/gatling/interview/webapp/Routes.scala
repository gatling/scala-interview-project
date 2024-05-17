package io.gatling.interview.webapp

import cats.effect._
import cats.syntax.all._
import hello._
import io.gatling.interview.api._
import io.gatling.interview.repository.ComputerRepository
import org.http4s._
import org.http4s.syntax.all._
import org.http4s.dsl.Http4sDsl
import org.http4s.headers.Location
import smithy4s.http4s.SimpleRestJsonBuilder

object Routes {
  private def computerDatabase(repository: ComputerRepository[IO]): Resource[IO, HttpRoutes[IO]] =
    SimpleRestJsonBuilder.routes(new ComputerDatabaseRoutes(repository)).resource

  private val example: Resource[IO, HttpRoutes[IO]] =
    SimpleRestJsonBuilder.routes(HelloWorldRoutes).resource

  private val docs: HttpRoutes[IO] =
    smithy4s.http4s.swagger.docs[IO](ComputerDatabaseEndpoints, HelloWorldEndpoints)

  private val main: HttpRoutes[IO] = {
    val dsl = new Http4sDsl[IO] {}
    import dsl._
    HttpRoutes.of { case GET -> Root =>
      IO.pure(
        Response[IO]()
          .withStatus(Found)
          .withHeaders(Location(uri"/docs"))
      )
    }
  }

  def all(repository: ComputerRepository[IO]): Resource[IO, HttpRoutes[IO]] = for {
    example <- example
    computerDatabase <- computerDatabase(repository)
  } yield computerDatabase <+> example <+> docs <+> main
}
