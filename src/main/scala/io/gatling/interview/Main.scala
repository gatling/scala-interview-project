package io.gatling.interview

import hello._
import cats.syntax.all._
import cats.effect._
import org.http4s.implicits._
import org.http4s.ember.server._
import org.http4s._
import com.comcast.ip4s._
import org.http4s.dsl.Http4sDsl
import org.http4s.headers.Location
import smithy4s.http4s.SimpleRestJsonBuilder
import scala.concurrent.duration._

object HelloWorldRoutes extends HelloWorldEndpoints[IO] {
  def hello(name: String, town: Option[String]): IO[Greeting] = IO.pure {
    town match {
      case None    => Greeting(s"Hello " + name + "!")
      case Some(t) => Greeting(s"Hello " + name + " from " + t + "!")
    }
  }
}

object Routes {
  private val example: Resource[IO, HttpRoutes[IO]] =
    SimpleRestJsonBuilder.routes(HelloWorldRoutes).resource

  private val docs: HttpRoutes[IO] =
    smithy4s.http4s.swagger.docs[IO](HelloWorldEndpoints)

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

  val all: Resource[IO, HttpRoutes[IO]] = example.map(_ <+> docs <+> main)
}

object Main extends IOApp.Simple {
  val run: IO[Unit] = Routes.all
    .flatMap { routes =>
      val thePort = port"9000"
      val theHost = host"localhost"
      val message =
        s"Server started on: $theHost:$thePort, press enter to stop"

      EmberServerBuilder
        .default[IO]
        .withPort(thePort)
        .withHost(theHost)
        .withHttpApp(routes.orNotFound)
        .withShutdownTimeout(1.second)
        .build
        .productL(IO.println(message).toResource)
    }
    .surround(IO.readLine)
    .void
    .guarantee(IO.println("Goodbye!"))
}
