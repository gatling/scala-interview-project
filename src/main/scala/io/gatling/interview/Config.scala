package io.gatling.interview

import com.twitter.util.Duration

import scala.concurrent.duration.FiniteDuration
import pureconfig.ConfigReader
import pureconfig.generic.semiauto.deriveReader

object Config {

  implicit val serverReader: ConfigReader[Server] = deriveReader
  implicit val configReader: ConfigReader[Config] = deriveReader
  implicit val durationReader: ConfigReader[Duration] = ConfigReader[FiniteDuration].map(fd => Duration.fromMilliseconds(fd.toMillis))

  final case class Server(
      port: Int,
      threadPoolSize: Int
  )

}

final case class Config(
    server: Config.Server
)
