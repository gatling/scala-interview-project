package io.gatling.interview

import io.gatling.interview.handler.ComputerHandler
import io.gatling.interview.http.api.ComputerDatabaseApi
import io.gatling.interview.repository.ComputerRepository

import cats.effect._
import cats.implicits._
import com.twitter.finagle.http.{ Request, Response }
import com.twitter.finagle.{ Http, ListeningServer, Service }
import com.twitter.util.Future
import io.chrisdavenport.log4cats.slf4j.Slf4jLogger
import io.finch.ToAsync
import pureconfig.ConfigSource
import pureconfig.module.catseffect.syntax.CatsEffectConfigSource

import java.util.concurrent.{ ExecutorService, Executors }
import io.gatling.interview.repository.CompanyRepository
import io.gatling.interview.handler.CompanyHandler

final class App[F[_]: ConcurrentEffect: ContextShift: Timer] {

  private val logger = Slf4jLogger.getLogger[F]
  private val configSource = ConfigSource.default.at("app")

  def program: F[Unit] =
    Blocker[F].use { blocker =>
      for {
        config <- configSource.loadF[F, Config](blocker)
        _ <- setup(config).use { server =>
          for {
            _ <- logger.info(
              s"Server started and bound to: ${server.boundAddress}"
            )
            _ <- ConcurrentEffect[F].never.as(())
          } yield ()
        }
      } yield ()
    }

  private def setup(config: Config): Resource[F, ListeningServer] =
    for {
      appResources <- appResources(config)
      computerRepository = new ComputerRepository[F]()
      computerHandler = new ComputerHandler[F](computerRepository)
      companyRepository = new CompanyRepository[F]()
      companyHandler = new CompanyHandler[F](companyRepository)
      api = new ComputerDatabaseApi[F](computerHandler, companyHandler)
      server <- server(
        api.service,
        config.server,
        appResources.serverExecutorService
      )
    } yield server

  private def server(
      service: Service[Request, Response],
      config: Config.Server,
      serverExecutorService: ExecutorService
  ): Resource[F, ListeningServer] =
    Resource.make(
      ConcurrentEffect[F].delay {
        Http.server
          .withStreaming(enabled = true)
          .withExecutionOffloaded(serverExecutorService)
          .serve(s":${config.port}", service)
      }
    )(s => Sync[F].suspend(implicitly[ToAsync[Future, F]].apply(s.close())))

  private def appResources(config: Config): Resource[F, AppResources] = {
    for {
      serverES <- createExecutorService(config.server.threadPoolSize)
    } yield AppResources(serverES)
  }

  private def createExecutorService(size: Int): Resource[F, ExecutorService] =
    Resource.make(
      ConcurrentEffect[F].delay(Executors.newFixedThreadPool(size))
    )(es => ConcurrentEffect[F].delay(es.shutdown()))

  private case class AppResources(serverExecutorService: ExecutorService)
}
