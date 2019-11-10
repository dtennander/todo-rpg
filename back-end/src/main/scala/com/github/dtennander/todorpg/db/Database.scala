package com.github.dtennander.todorpg.db

import cats.effect.{Async, Blocker, ContextShift, Resource, Sync}
import com.github.dtennander.todorpg.DatabaseConfig
import doobie.hikari.HikariTransactor
import doobie.util.ExecutionContexts
import org.flywaydb.core.Flyway

object Database {
  def transactor[F[_]: Async: ContextShift](config: DatabaseConfig): Resource[F, HikariTransactor[F]] = for {
    ce <- ExecutionContexts.fixedThreadPool[F](32) // our connect EC
    be <- Blocker[F]
    urlEnd = config.database.map(db => s"/$db").getOrElse("")
    xa <- HikariTransactor.newHikariTransactor[F](
      "org.postgresql.Driver",
      s"jdbc:postgresql://${config.host}:${config.port}${urlEnd}",
      config.username,
      config.password,
      ce,
      be
    )
  } yield xa

  def initialize[F[_]: Sync](transactor: HikariTransactor[F]): F[Unit] = {
    transactor.configure { dataSource =>
      Sync[F].delay {
        val flyWay = Flyway.configure()
                .locations("/db/migrations")
                .dataSource(dataSource)
                .load()
        flyWay.migrate()
        ()
      }
    }
  }
}
