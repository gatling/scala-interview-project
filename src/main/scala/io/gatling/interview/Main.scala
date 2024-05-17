package io.gatling.interview

import cats.syntax.all._
import cats.effect._
import org.http4s.implicits._
import org.http4s.ember.server._
import com.comcast.ip4s._
import io.gatling.interview.repository.ComputerRepository
import io.gatling.interview.webapp.Routes

import scala.concurrent.duration._

object Main extends IOApp.Simple {
  val run: IO[Unit] = Routes
    .all(ComputerRepository[IO]())
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
