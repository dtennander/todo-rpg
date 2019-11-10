package com.github.dtennander.todorpg

import cats.effect._
import cats.implicits._
import com.github.dtennander.todorpg.auth.{GoogleTokenValidator, TokenValidator}
import com.github.dtennander.todorpg.db.Database
import com.github.dtennander.todorpg.endpoints.lists.ListsResource
import com.github.dtennander.todorpg.endpoints.todos.TodosResource
import com.github.dtennander.todorpg.endpoints.users.UsersResource
import com.github.dtennander.todorpg.users.UserHandlerImpl
import doobie.hikari.HikariTransactor
import fs2.Stream
import org.http4s.HttpRoutes
import org.http4s.implicits._
import org.http4s.server.blaze.BlazeServerBuilder
import org.http4s.server.middleware.{CORS, Logger}

object Backend extends IOApp {

  def stream[F[_]:Timer :ConcurrentEffect: ContextShift :Effect]: Stream[F, Nothing] = {
      for {
          config <- Stream.eval(Config.create[F])
          validator <- Stream.eval(GoogleTokenValidator.withToken[F](config.googleClientId))
          transactor <- Stream.resource(Database.transactor(config.databaseConfig))
          _ <- Stream.eval(Database.initialize(transactor))
          httpApp <- Stream(addMiddleware(routes(validator, transactor)).orNotFound)
          finalHttpApp = Logger.httpApp(logHeaders = true, logBody = true)(httpApp)
          exitCode <- BlazeServerBuilder[F]
            .bindHttp(config.port, "0.0.0.0")
            .withHttpApp(finalHttpApp)
            .serve
      } yield exitCode
    }.drain

  private def addMiddleware[F[_]: Effect](routes: HttpRoutes[F]) = {
    CORS(routes)
  }

  private def routes[F[_]: Effect](
             validator: TokenValidator[F],
             transactor: HikariTransactor[F]): HttpRoutes[F] =
      new UsersResource[F]().routes(new UserHandlerImpl[F](validator)) <+>
      new ListsResource[F]().routes(new lists.ListHandlerImpl[F]()) <+>
      new TodosResource[F]().routes(new todos.TodosHandlerImpl[F]())

  def run(args: List[String]): IO[ExitCode] = Backend.stream.compile.drain.as(ExitCode.Success)
}
