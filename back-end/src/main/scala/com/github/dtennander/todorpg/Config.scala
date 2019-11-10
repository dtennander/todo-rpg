package com.github.dtennander.todorpg

import cats.effect.Sync
import cats.implicits._

case class Config(databaseConfig: DatabaseConfig, port: Int, googleClientId: String)


object Config {
    private val databaseUrl = "postgres://postgres:postgres@localhost:54320/mydb"
    private val defaultClientId = "848941796451-a83bla4b2vk7oqvrbti9tat7urvqd41r.apps.googleusercontent.com"
    private val defaultPort = 8080

    def create[F[_]: Sync]: F[Config] = for {
      ci <- Sync[F].delay(sys.env("GOOGLE_CLIENT_ID")).attempt
      clientId = ci match {
          case Right (id) => id
          case _ => defaultClientId
      }
      databaseUri <- Sync[F].delay(sys.env("DATABASE_URL")).attempt
      dUri = DatabaseConfig.fromUrl(databaseUri match {
          case Right(d) => d
          case Left(_) => databaseUrl
      })
      port <- Sync[F].delay(Integer.valueOf(sys.env("PORT"))).attempt
      p = port match {
          case Right(p) => p.toInt
          case Left(_) => defaultPort
      }
    } yield Config(dUri, p, clientId)


}
